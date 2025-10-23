package kware.apps.thirdeye.mobigen.datasetfile.service;

import cetus.user.UserUtil;
import cetus.util.WebUtil;
import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFileDao;
import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFileLog;
import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFileLogDao;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFileView;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileList;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import kware.common.config.auth.dto.SessionUserInfo;
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
public class CetusDatasetFileService {

    private final CetusTusDatasetFileService tusDatasetFileService;
    private final CetusDatasetFileDao fileDao;
    private final CetusDatasetFileLogDao logDao;

    @Value("${tus.server.storage.temp}")
    private String tusTemp;

    public Long generateUid() {
        return fileDao.key();
    }

    public boolean ifFileIdExist(HttpServletRequest req) {
        return !req.getParameter("fileId").equals("null")
                && !req.getParameter("fileId").equals("undefined")
                && req.getParameter("fileId").length() != 0;
    }

    @Transactional(readOnly = true)
    public ResponseEntity fileView(final HttpServletRequest req) {
        String fileId = req.getParameter("fileId");
        if(!this.ifFileIdExist(req)) return ResponseEntity.ok("fileId is not found");
        else {

            // 1. { fileId } -> 파일 정보
            CetusDatasetFile datasetFile = fileDao.getFileInfoByFileId(fileId);
            if (datasetFile == null || !new File(datasetFile.getFilePath()).exists()) {
                return ResponseEntity.notFound().build();
            }

            // 2.{file} contentType
            File fileObj = new File(datasetFile.getFilePath());
            String contentType = datasetFile.getFileType();
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
            CetusDatasetFile datasetFile = fileDao.getFileInfoByFileId(fileId);
            if (datasetFile == null || !new File(datasetFile.getFilePath()).exists()) {
                return ResponseEntity.notFound().build();
            }

            // 2.{file} contentType
            File fileObj = new File(datasetFile.getFilePath());
            String contentType = datasetFile.getFileType();
            if (contentType == null || contentType.isBlank()) {
                contentType = "application/octet-stream";
            }

            // 3. file log
            SessionUserInfo user = UserUtil.getUser(req);
            String workerUid = user != null ? user.getUid().toString() : WebUtil.getIpAddress(req);
            String workerNm = user != null ? user.getUserNm() : WebUtil.getIpAddress(req);
            CetusDatasetFileLog datasetFileLog = new CetusDatasetFileLog(datasetFile.getFileUid(), datasetFile.getFileId(), workerUid, workerNm);

            // 4. insert and update download count
            fileDao.increaseDownCnt(datasetFile);
            logDao.insertLog(datasetFileLog);

            // 5. return
            String encodedFileName = URLEncoder.encode(datasetFile.getOrgFileNm(), StandardCharsets.UTF_8).replace("+", "%20");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(new FileSystemResource(fileObj));
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

    @Transactional(readOnly = true)
    public Boolean checkFile(HttpServletRequest req) {
        String fileId = req.getParameter("fileId");
        if(!this.ifFileIdExist(req)) return Boolean.FALSE;
        else {

            CetusDatasetFile datasetFile = fileDao.getFileInfoByFileId(fileId);

            String realPath = datasetFile.getFilePath();
            File file2 = new File(realPath);

            if (file2.exists() && file2.isFile()) return Boolean.TRUE;

            return Boolean.FALSE;
        }
    }

    @Transactional(readOnly = true)
    public List<CetusDatasetFileView> findDataFile(SearchDatasetFile search) {
        return fileDao.getDataFile(search);
    }

    @Transactional(readOnly = true)
    public List<CetusDatasetFileList> list(CetusDatasetFile datasetFile) {
        List<CetusDatasetFileList> resList = fileDao.getFileList(datasetFile);
        resList.stream().forEach(cfile -> cfile.setFilePath(EncryptionUtil.encrypt(cfile.getFilePath())));
        return resList;
    }

    @Transactional(readOnly = true)
    public CetusDatasetFileView findRawdataFileView(SearchDatasetFileView search) {
        return fileDao.getRawdataFileView(search);
    }

    @Transactional
    public void processAddFile(CetusDatasetFile[] fileAdd) {

        if (fileAdd != null) {
            for (CetusDatasetFile f : fileAdd) {
                f.setRegId(UserUtil.getUser().getUserId());

                try {
                    f = tusDatasetFileService.moveToDefaultStorage(f);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    continue;
                }

                f.setSaved(CommonFileState.Y.name());
                fileDao.insert(f);
            }
        }
    }

    @Transactional
    public void processDelFile(String fileId) {
        fileDao.deleteFile(new CetusDatasetFile(fileId)); //논리적인 삭제: 물리적인 파일을 삭제하지 않는다.
    }
}
