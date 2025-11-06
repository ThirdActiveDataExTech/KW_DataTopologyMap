package kware.apps.thirdeye.mobigen.approveddataset.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
* @fileName     SearchMetadataApproved
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-29
* @summary      {workplaceUid} 하위에 {metadataId} 메타데이터가 케이웨어에서 진열관리중인지 여부 체크
**/
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMetadataApproved {

    private String metadataId;
    private Long workplaceUid;

    public SearchMetadataApproved(String metadataId, Long workplaceUid) {
        this.metadataId = metadataId;
        this.workplaceUid = workplaceUid;
    }
}
