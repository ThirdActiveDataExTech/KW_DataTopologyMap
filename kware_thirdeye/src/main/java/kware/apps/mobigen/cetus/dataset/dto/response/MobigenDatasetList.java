package kware.apps.mobigen.cetus.dataset.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobigenDatasetList {

    private Long uid;
    private String title;
    private String description;
    private String registrantId;
    private String regDt;

}
