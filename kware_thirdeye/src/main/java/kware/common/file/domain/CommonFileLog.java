package kware.common.file.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonFileLog {

    private Long fileUid;
    private String fileId;
    private Long logUid;

    private String workerUid;
    private String workerNm;

    private Integer downCnt;

    private String regDt;
    private String downloadUrl;

    public CommonFileLog(Long fileUid, String fileId) {
        this.fileUid = fileUid;
        this.fileId = fileId;
    }

    public void setWorkerInfo(String workerUid, String workerNm) {
        this.workerUid = workerUid;
        this.workerNm = workerNm;
    }

}
