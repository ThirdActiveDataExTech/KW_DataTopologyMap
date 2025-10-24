package kware.apps.mobigen.integration.dto.response.metadata;

import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MetadataList {

    private String metadataId;

    // from. mobigen
    private MetadataResultResponse metadataView;

    // from. kware
    private boolean isRegistered;   // KWARE 포탈시스템을 통한 등록, 자동등록 여부
    private String registrantId;    // 등록자
    private boolean isApproved;     // 이미 승인된 데이터인지

    public MetadataList(String metadataId) {
        this.metadataId = metadataId;
    }

    public void setRegistrantInfo(boolean isRegistered, String registrantId) {
        this.isRegistered = isRegistered;
        this.registrantId = registrantId;
    }
}
