package kware.common.file.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
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

}
