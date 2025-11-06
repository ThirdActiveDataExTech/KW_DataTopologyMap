package kware.apps.thirdeye.mobigen.datasetui.dto.response;


import kware.apps.thirdeye.mobigen.mainui.domain.DatasetMainUiType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetUiGroup {

    private Long mainUiUid;
    private String mainUiCode;
    private String typeCd;
    private String typeCdDescription;
    private Integer maxCount;
    private Integer categoryCnt;
    private Integer titleLineClamp;
    private Integer descLineClamp;
    private String thumbUseAt;
    private boolean useCategory;
    private boolean useTitle;
    private boolean useSummary;
    private Integer usedCount;

    public void setUseInfo(DatasetMainUiType mainUiType) {
        this.useCategory = mainUiType.isUseCategory();
        this.useTitle = mainUiType.isUseTitle();
        this.useSummary = mainUiType.isUseSummary();
    }
}
