package kware.common.file.service;


import cetus.bean.FileBean;
import cetus.user.UserUtil;
import cetus.util.WebUtil;
import kware.common.config.auth.PrincipalDetails;
import kware.common.config.auth.dto.SessionUserInfo;
import kware.common.file.domain.CommonFile;
import kware.common.file.domain.CommonFileDao;
import kware.common.file.domain.CommonFileLog;
import kware.common.file.domain.CommonFileState;
import kware.common.file.tus.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonFileService {

    private final CetusTusFileService cetusTusFileService;
    private final CommonFileDao dao;

    @Value("${tus.server.storage.temp}")
    private String tusTemp;

    public Long generateUid() {
        return dao.key();
    }

    public CommonFile view(CommonFile bean) {
        return dao.view(bean);
    }

    public List<CommonFile> list(CommonFile bean) {
        bean.setSaved(CommonFileState.Y.name());
        List<CommonFile> resList = dao.list(bean);
        //파일경로를 암호화 해서 base64로 인코딩한 후에
        resList.stream().forEach(cfile -> cfile.setFilePath(EncryptionUtil.encrypt(cfile.getFilePath())));
        return resList;
    }

    public List<CommonFile> findCommonFileListByFileUid(Long fileUid) {
        return dao.getCommonFileListByFileUid(fileUid);
    }

    public boolean ifFileIdExist(HttpServletRequest req) {
        return !req.getParameter("fileId").equals("null")
                && !req.getParameter("fileId").equals("undefined")
                && req.getParameter("fileId").length() != 0;
    }

    public ResponseEntity fileView(final HttpServletRequest req) {
        String fileId = req.getParameter("fileId");
        if(!this.ifFileIdExist(req)) return ResponseEntity.ok("fileId is not found");
        else {

            // 1. {fileId} -> 파일 정보
            CommonFile commonFile = dao.findFileInfo(new CommonFile(fileId));
            if (commonFile == null || !new File(commonFile.getFilePath()).exists()) {
                return ResponseEntity.notFound().build();
            }

            // 2.{file} contentType
            File fileObj = new File(commonFile.getFilePath());
            String contentType = commonFile.getFileType();
            if (contentType == null || contentType.isBlank()) {
                contentType = "application/octet-stream";
            }

            // 3. return
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(new FileSystemResource(fileObj));
        }
    }

    @Transactional
    public ResponseEntity download(final HttpServletRequest req) {
        String fileId = req.getParameter("fileId");
        if(!this.ifFileIdExist(req)) return ResponseEntity.ok("fileId is not found");
        else {

            // 1. {fileId} -> 파일 정보
            CommonFile commonFile = new CommonFile(fileId);
            commonFile = dao.findFileInfo(commonFile);
            if (commonFile == null || !new File(commonFile.getFilePath()).exists()) {
                return ResponseEntity.notFound().build();
            }

            // 2.{file} contentType
            File fileObj = new File(commonFile.getFilePath());
            String contentType = commonFile.getFileType();
            if (contentType == null || contentType.isBlank()) {
                contentType = "application/octet-stream";
            }

            // 3. file log
            SessionUserInfo user = UserUtil.getUser(req);
            String workerUid = user != null ? user.getUid().toString() : WebUtil.getIpAddress(req);
            String workerNm = user != null ? user.getUserNm() : WebUtil.getIpAddress(req);
            CommonFileLog commonFileLog = new CommonFileLog(commonFile, workerUid, workerNm);

            // 4. insert and update download count
            dao.increaseDownCnt(commonFile);
            dao.insertLog(commonFileLog);

            // 5. return
            String encodedFileName = URLEncoder.encode(commonFile.getOrgFileNm(), StandardCharsets.UTF_8).replace("+", "%20");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(new FileSystemResource(fileObj));
        }
    }

    public Boolean checkFile(HttpServletRequest req) {
        String fileId = req.getParameter("fileId");
        if(!this.ifFileIdExist(req)) return Boolean.FALSE;
        else {

            CommonFile commonFile = new CommonFile();
            commonFile.setFileId(fileId);
            commonFile = dao.findFileInfo(commonFile);

            String realPath = commonFile.getFilePath();
            File file2 = new File(realPath);

            if (file2.exists() && file2.isFile()) return Boolean.TRUE;

            return Boolean.FALSE;
        }
    }

    @Transactional
    public ResponseEntity downloadTemp(final HttpServletRequest req) {
        String fileId = req.getParameter("fileId");
        if(!this.ifFileIdExist(req)) return ResponseEntity.ok("fileId is not found");
        else {

            File file2 = new File(tusTemp + File.separator + fileId);
            String filePath = file2.getAbsolutePath();
            String fileName = file2.getName();

            Resource resource = new FileSystemResource(filePath);

            if (!file2.exists() || !file2.isFile()) {
                log.error("파일이 존재하지 않습니다. filepath:{}, orgFileNam:{}", filePath, fileName);
            }

            String disposition = "inline";

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", disposition)
                    .body(resource);
        }
    }

    @Transactional
    public <T extends FileBean> Long processFileBean(T bean, SessionUserInfo user, Long fileUid) {
        if (bean.getFileAdd() != null) {
            for (CommonFile f : bean.getFileAdd()) {
                if(fileUid == null) {
                    fileUid = this.generateUid();
                }
                f.setRegId(user.getUserId());
                f.setFileUid(fileUid);
                try {
                    f = cetusTusFileService.moveToDefaultStorage(f);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    continue;
                }
                f.setSaved(CommonFileState.Y.name());
                dao.insert(f);
            }
        }

        if (bean.getFileDel() != null) {
            for (CommonFile f : bean.getFileDel()) {
                f.setFileId(f.getFileId());
                dao.delete(f); //논리적인 삭제: 물리적인 파일을 삭제하지 않는다.
            }
        }

        return fileUid;
    }

    @Transactional
    public <T extends FileBean> Long processFile2(CommonFile[] fileAdd, CommonFile[] fileDel, SessionUserInfo user, Long fileUid) {
        if (fileAdd != null) {
            for (CommonFile f : fileAdd) {
                if(fileUid == null) {
                    fileUid = this.generateUid();
                }
                f.setRegId(user.getUserId());
                f.setFileUid(fileUid);
                try {
                    f = cetusTusFileService.moveToDefaultStorage(f);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    continue;
                }
                f.setSaved(CommonFileState.Y.name());
                dao.insert(f);
            }
        }

        if (fileDel != null) {
            for (CommonFile f : fileDel) {
                f.setFileId(f.getFileId());
                dao.delete(f); //논리적인 삭제: 물리적인 파일을 삭제하지 않는다.
            }
        }

        return fileUid;
    }

    @Transactional
    public Long processFileSeparately(List<CommonFile> fileAdd, List<CommonFile> fileDel, Long fileUid) {
        if (fileAdd != null) {
            for (CommonFile f : fileAdd) {
                if(fileUid == null) {
                    fileUid = this.generateUid();
                }

                String userId = "비화원유저";
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (UserUtil.isAuthenticated(authentication)) {
                    PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
                    userId = details.getUser().getUserId();
                }
                f.setRegId(userId);
                f.setFileUid(fileUid);

                try {
                    f = cetusTusFileService.moveToDefaultStorage(f);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    continue;
                }
                f.setSaved(CommonFileState.Y.name());
                dao.insert(f);
            }
        }

        if (fileDel != null) {
            for (CommonFile f : fileDel) {
                f.setFileId(f.getFileId());
                dao.delete(f); //논리적인 삭제: 물리적인 파일을 삭제하지 않는다.
            }
        }

        return fileUid;
    }
}
