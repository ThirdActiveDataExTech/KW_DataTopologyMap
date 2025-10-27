package kware.apps.thirdeye.mobigen.datasetfile.domain.file;

import kware.apps.thirdeye.mobigen.datasetfile.enumcd.DataFileTpCd;
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

    public void setPackageDto(String metadataId, String rawdataId, DataFileTpCd fileTpCd) {
        this.metadataId = metadataId;
        this.rawdataId = rawdataId;
        this.dataTpCd = fileTpCd.PACKAGE.name();
    }

    public void setMetadataDto(String metadataId, DataFileTpCd fileTpCd) {
        this.metadataId = metadataId;
        this.dataTpCd = fileTpCd.METADATA.name();
    }

    public void setRawdataDto(String metadataId, String rawdataId, DataFileTpCd fileTpCd) {
        this.metadataId = metadataId;
        this.rawdataId = rawdataId;
        this.dataTpCd = fileTpCd.RAWDATA.name();
    }
}
