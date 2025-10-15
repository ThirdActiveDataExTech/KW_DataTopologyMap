package kware.apps.mobigen.cetus.dataset.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @fileName     MobigenDatasetRealDataView
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      모비젠 데이터셋 원본(실)데이터 파일 정보 응답 dto
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobigenDatasetRealDataView {

    private String fileId;
    private String orgFileNm;
    private String filePath;
    private Long   fileSize;
    private String fileType;
    private String extension;
    private Integer downCnt;
    private String regDt;

}
