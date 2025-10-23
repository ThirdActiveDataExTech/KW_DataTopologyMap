package kware.apps.thirdeye.mobigen.approveddataset.domain;

import lombok.Getter;

@Getter
public enum ApprovedDatasetTargetTpCd {

    MOBIGEN("모비젠"),
    KWARE("케이웨어");

    private String description;

    ApprovedDatasetTargetTpCd(String description) {
        this.description = description;
    }

}
