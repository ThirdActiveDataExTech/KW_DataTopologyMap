package kware.apps.thirdeye.mobigen.datasetfile.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDatasetFileView {

    private Long fileUid;
    private String fileId;
    private String fileNm;
    private String orgFileNm;
    private String filePath;
    private String fileUrl;
    private Long fileSize;
    private String fileType;
    private String extension;
    private Integer downCnt;
    private String useAt;
    private String regDt;
    private String updtDt;
    private String saved;
    private String regId;
    private String metadataId;
    private String rawdataId;
    private String dataTpCd;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
