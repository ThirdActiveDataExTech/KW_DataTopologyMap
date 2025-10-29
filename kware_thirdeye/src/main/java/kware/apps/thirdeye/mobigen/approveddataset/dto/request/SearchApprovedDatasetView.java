package kware.apps.thirdeye.mobigen.approveddataset.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @fileName     SearchApprovedDatasetView
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-29
* @summary      진열 등록된 데이터셋 상세 정보 조회
 *              => 이때 {userUid} 값을 통해 해당 데이터셋에 대해 현재 로그인한 사용자의 북마크 여부까지 체크
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchApprovedDatasetView {

    private Long approvedUid;
    private Long userUid;

    public SearchApprovedDatasetView(Long approvedUid, Long userUid) {
        this.approvedUid = approvedUid;
        this.userUid = userUid;
    }
}
