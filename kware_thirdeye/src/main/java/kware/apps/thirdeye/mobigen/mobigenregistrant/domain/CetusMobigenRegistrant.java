package kware.apps.thirdeye.mobigen.mobigenregistrant.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @fileName     CetusMobigenRegistrant
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-13
* @summary      모비젠 측에 데이터셋 등록 시점에 등록자 정보를 그쪽에 저장이 불가능하여
 *              등록자 정보는 승인 이전 시점이더라도 kware 포탈 시스템에서 등록/관리하기 위한 도메인
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusMobigenRegistrant {

    private Long uid;
    private String metadataId;
    private Long registrantUid;
    private String regDt;

    public CetusMobigenRegistrant(String metadataId, Long registrantUid) {
        this.metadataId = metadataId;
        this.registrantUid = registrantUid;
    }
}
