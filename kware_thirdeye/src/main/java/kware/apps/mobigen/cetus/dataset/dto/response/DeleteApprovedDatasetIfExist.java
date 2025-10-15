package kware.apps.mobigen.cetus.dataset.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteApprovedDatasetIfExist {

    private Long datasetId;
    private Long workplaceUid;

    public DeleteApprovedDatasetIfExist(Long datasetId, Long workplaceUid) {
        this.datasetId = datasetId;
        this.workplaceUid = workplaceUid;
    }
}
