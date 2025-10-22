package kware.apps.thirdeye.mobigen.datasetfile.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDatasetFileLog {

    private Long logUid;
    private Long fileUid;
    private String fileId;
    private String workerUid;
    private String workerNm;
    private String downloadUrl;

    public CetusDatasetFileLog(Long fileUid, String fileId, String workerUid, String workerNm) {
        this.fileUid = fileUid;
        this.fileId = fileId;
        this.workerUid = workerUid;
        this.workerNm = workerNm;
    }
}
