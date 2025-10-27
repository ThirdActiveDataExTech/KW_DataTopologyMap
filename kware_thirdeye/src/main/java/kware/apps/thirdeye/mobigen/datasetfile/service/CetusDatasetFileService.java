package kware.apps.thirdeye.mobigen.datasetfile.service;

import cetus.user.UserUtil;
import cetus.util.WebUtil;
import kware.apps.mobigen.mobigen.dto.response.rawdata.RawdataListItemResponse;
import kware.apps.thirdeye.mobigen.datasetfile.domain.file.CetusDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.domain.file.CetusDatasetFileDao;
import kware.apps.thirdeye.mobigen.datasetfile.domain.log.CetusDatasetFileLog;
import kware.apps.thirdeye.mobigen.datasetfile.domain.log.CetusDatasetFileLogDao;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.DeleteDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFilePage;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFileView;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileList;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import kware.common.config.auth.dto.SessionUserInfo;
import kware.common.file.domain.CommonFileState;
import kware.common.file.tus.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusDatasetFileService {

    private final CetusTusDatasetFileService tusDatasetFileService;
    private final CetusDatasetFileDao fileDao;
    private final CetusDatasetFileLogDao logDao;

    public Long generateUid() {
        return fileDao.key();
    }

    @Transactional(readOnly = true)
    public List<CetusDatasetFileList> list(CetusDatasetFile datasetFile) {
        List<CetusDatasetFileList> resList = fileDao.getFileList(datasetFile);
        resList.stream().forEach(cfile -> cfile.setFilePath(EncryptionUtil.encrypt(cfile.getFilePath())));
        return resList;
    }

    public boolean ifFileIdExist(HttpServletRequest req) {
        return !req.getParameter("fileId").equals("null")
                && !req.getParameter("fileId").equals("undefined")
                && req.getParameter("fileId").length() != 0;
    }

    @Transactional
    public ResponseEntity downloadMetaFile(String fileId, final HttpServletRequest req) {
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
    public List<CetusDatasetFileView> findDataFileList(SearchDatasetFile search) {
        return fileDao.getDataFile(search);
    }

    @Transactional(readOnly = true)
    public CetusDatasetFileView findRawdataFileView(SearchDatasetFileView search) {
        return fileDao.getRawdataFileView(search);
    }

    @Transactional
    public Map<String, String> processAddFile(CetusDatasetFile f) {

        Map<String, String> map = new HashMap<>();

        if (f != null) {
            f.setRegId(UserUtil.getUser().getUserId());

            try {
                f = tusDatasetFileService.moveToDefaultStorage(f);
            } catch (IOException e) {
                log.error(e.toString(), e);
            }

            f.setSaved(CommonFileState.Y.name());
            fileDao.insert(f);

            map.put("fileId", f.getFileId());
            map.put("filePath", f.getFilePath());
        }

        return map;
    }

    @Transactional
    public void processDelFile(DeleteDatasetFile request) {
        fileDao.deleteFile(request);
    }

    /* =================== */

    @Transactional(readOnly = true)
    public List<RawdataListItemResponse> findDataFilePage(SearchDatasetFilePage search) {
        return fileDao.getDataFilePage(search);
    }
    @Transactional(readOnly = true)
    public int findDataFilePageCount(SearchDatasetFilePage search) {
        return fileDao.getDataFilePageCount(search);
    }
}
