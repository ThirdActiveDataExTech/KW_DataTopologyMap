package kware.apps.mobigen.cetus.dataset.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @fileName     MobigenDatasetList
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      모비젠 데이터셋 목록 조회 응답 dto
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobigenDatasetList {

    private Long uid;
    private String title;
    private boolean isRegistered;
    private String registrantId;
    private String regDt;

    public void setRegistrantInfo(boolean isRegistered, String registrantId) {
        this.isRegistered = isRegistered;
        this.registrantId = registrantId;
    }
}
