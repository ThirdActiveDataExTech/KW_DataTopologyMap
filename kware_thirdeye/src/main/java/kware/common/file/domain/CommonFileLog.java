package kware.common.file.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public CommonFileLog(CommonFile commonFile, String workerUid, String workerNm){
        this.fileUid = commonFile.getFileUid();
        this.fileId = commonFile.getFileId();
        this.workerUid = workerUid;
        this.workerNm = workerNm;
    }

}
