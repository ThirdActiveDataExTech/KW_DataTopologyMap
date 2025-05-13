package kware.common.file.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonFile {

    private Long   fileUid;
    private String fileId;
    private String fileNm;  //서버저장된 이름인데 fileId로 대체
    private String orgFileNm; //원본파일명
    private String filePath;
    private String fileUrl;  //사용하지 않는 방향으로 수정
    private Long   fileSize;
    private String fileType;
    private String extension;
    private String downCnt;

    private String regId;
    private String regDt;

    private String saved;

    // file log
    private Long workerUid;
    private String workerNm;
    private String type;
}
