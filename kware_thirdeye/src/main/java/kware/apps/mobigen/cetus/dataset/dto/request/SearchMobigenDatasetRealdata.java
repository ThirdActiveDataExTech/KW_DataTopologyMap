package kware.apps.mobigen.cetus.dataset.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName     SearchMobigenDatasetRealdata
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-21
* @summary      모비젠 데이터셋 원본(실)데이터 파일 정보 목록 요청 dto
**/

@Getter @Setter
public class SearchMobigenDatasetRealdata {
    private Integer pageNumber;
    private Integer size;
}
