package kware.apps.mobigen.cetus.dataset.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMobigenDataset {

    private String registrantId;

    public SearchMobigenDataset(String registrantId) {
        this.registrantId = registrantId;
    }
}
