package kware.apps.thirdeye.mobigen.mainui.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum DatasetMainUiType {

    TYPE_A("카테고리 + 백그라운드 이미지 + 정보(제목)", true, true, false, true),
    TYPE_B("카테고리 + 백그라운드 이미지 + 액션버튼 + 정보(제목 및 설명)", true, true, true, true),
    TYPE_C("백그라운드 이미지 + 정보(제목 및 설명)", false, true, true, true),
    TYPE_D("정보(제목 및 설명)", false,true, true, false);

    private String description;
    private boolean useCategory;
    private boolean useTitle;
    private boolean useSummary;
    private boolean useThumbNail;

    DatasetMainUiType(String description, boolean useCategory, boolean useTitle, boolean useSummary, boolean useThumbNail) {
        this.description = description;
        this.useCategory = useCategory;
        this.useTitle = useTitle;
        this.useSummary = useSummary;
        this.useThumbNail = useThumbNail;
    }

    public static Map<String, Map<String, Object>> toMap() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(
                        Enum::name,
                        type -> {
                            Map<String, Object> info = new LinkedHashMap<>();
                            info.put("description", type.getDescription());
                            info.put("useCategory", type.isUseCategory());
                            info.put("useThumbNail", type.isUseThumbNail());
                            info.put("useTitle", type.isUseTitle());
                            info.put("useSummary", type.isUseSummary());
                            return info;
                        },
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
