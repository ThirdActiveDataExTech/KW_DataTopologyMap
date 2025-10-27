package kware.apps.thirdeye.mobigen.datasetfile.service;

import kware.apps.thirdeye.mobigen.datasetfile.domain.file.CetusDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.enumcd.CetusDatasetFileState;
import kware.common.file.tus.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusTusDatasetFileService {

    private final static SimpleDateFormat YYYYMM_format = new SimpleDateFormat("yyyy" + File.separator + "MM");
    private final FileUploadService fileUploadService;

    /**
     * 현재 파일이 temp경로에 있으면 파일을 절대경로로 변경하고 파일 경로 및 사이즈 설정
     *
     * @param cFile
     * @return
     */
    private CetusDatasetFile makeFileInfo(CetusDatasetFile cFile) throws IOException {

        if (CetusDatasetFileState.N.name().equals(cFile.getSaved())) {

            //저장전 파일
            String dir = fileUploadService.getStoragePath_temp();
            String fileId = cFile.getFileId();
            File tempFile = new File(dir + File.separator + fileId);
            if (!tempFile.exists() && !tempFile.isFile()) {
                log.error("파일경로에 파일이 없습니다. fileId: {}", cFile.getFileId());
                throw new FileNotFoundException("File not found:" + tempFile.getPath());
            }
            cFile.setFileSize(tempFile.length());
            cFile.setFilePath(tempFile.getAbsolutePath());

        } else {

            // 저장했던 파일의 사이즈 만 다시 확인하는 정도
            if (cFile.getFileSize() == null || cFile.getFileSize() == 0) {
                File tempFile = new File(cFile.getFilePath());
                if (!tempFile.exists() && !tempFile.isFile()) {
                    log.error("파일경로에 파일이 없습니다. fileId: {}", cFile.getFileId());
                    throw new FileNotFoundException("File not found:" + tempFile.getPath());
                }
                cFile.setFileSize(tempFile.length());
            }

        }
        return cFile;
    }


    /**
     * {CetusDatasetFile} 의 {filepath}의 정보를 기본 날짜별 저장소로 이동: 파일 이동, 사이즈, 경로 설정
     * 외부에서 개별적을 사용할 수 있도록 함, 단일파일 처리
     */
    public CetusDatasetFile moveToDefaultStorage(CetusDatasetFile cFile) throws IOException {

        CetusDatasetFile mFile = makeFileInfo(cFile);
        String dataFolder = fileUploadService.getStoragePath_data();
        Date cd = new Date();
        String subfolder = YYYYMM_format.format(cd);
        String reFilename = mFile.getFileId();
        File targetFile = new File(dataFolder + File.separator + subfolder, reFilename);
        mFile = moveToRequestStorage(mFile, targetFile.getAbsolutePath());
        return mFile;
    }

    /**
     * {CetusDatasetFile}의 {filepath}의 정보를 요청한 파일 폴더로 이동: 파일 이동, 사이즈, 경로 설정
     * * 외부에서 개별적을 사용할 수 있도록 함, 단일파일 처리
     *
     * @param cFile
     * @param absolutePath
     * @return
     * @throws IOException
     */
    public CetusDatasetFile moveToRequestStorage(CetusDatasetFile cFile, String absolutePath) throws IOException {
        cFile = makeFileInfo(cFile);

        Path tempFilePath = Paths.get(cFile.getFilePath());
        File targetFile = new File(absolutePath);

        if (!targetFile.getParentFile().exists()) targetFile.getParentFile().mkdirs();
        if (targetFile.exists() && targetFile.isFile()) targetFile.delete();

        Files.move(tempFilePath, targetFile.toPath());

        if (log.isDebugEnabled()) log.debug("moveTempToStorage:{}", absolutePath);

        cFile.setFilePath(absolutePath);
        return cFile;
    }
}
