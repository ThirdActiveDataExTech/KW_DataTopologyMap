package kware.apps.thirdeye.mobigen.datasetfile.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CetusDatasetFile {

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
    private String saved;
    private String regId;
    private String metadataId;
    private String rawdataId;
    private String dataTpCd;

    public CetusDatasetFile(String fileId) {
        this.fileId = fileId;
    }
}
