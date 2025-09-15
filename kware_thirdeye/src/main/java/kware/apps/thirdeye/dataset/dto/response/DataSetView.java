package kware.apps.thirdeye.dataset.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetView {

    private Long datasetId;
    private String title;
    private String description;
    private String metadata;
}
