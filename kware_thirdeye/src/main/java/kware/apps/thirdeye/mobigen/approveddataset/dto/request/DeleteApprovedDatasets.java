package kware.apps.thirdeye.mobigen.approveddataset.dto.request;


import lombok.AccessLevel;
import cetus.annotation.DisplayName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

import java.util.List;

/**
* @fileName     DeleteApprovedDatasets
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-14
* @summary      KWARE 포탈 시스템에 진열관리/승인된 데이터셋 삭제 (논리)
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteApprovedDatasets {
    @NotEmpty @DisplayName("삭제 대상")
    private List<Long> uids;
}
