package kware.apps.thirdeye.mobigen.datasetui.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
* @fileName     ChangeShowUiDatasets
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-17
* @summary      KWARE 포탈 시스템에 진열관리/승인된 데이터셋 공개여부 수정
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeShowUiDatasets {
    private List<Long> uids;            // approved_data_uid`s
}
