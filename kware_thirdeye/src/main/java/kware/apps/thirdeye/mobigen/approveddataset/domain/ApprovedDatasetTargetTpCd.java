package kware.apps.thirdeye.mobigen.approveddataset.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum ApprovedDatasetTargetTpCd {

    MOBIGEN("모비젠"),
    KWARE("케이웨어");

    private String description;

    ApprovedDatasetTargetTpCd(String description) {
        this.description = description;
    }

    public static Map<String, String> toMap() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(ApprovedDatasetTargetTpCd::getDescription, Enum::name));
    }
}
