package kware.apps.thirdeye.mobigen.mainui.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum DatasetMainUiType {

    TYPE_A(1, "카테고리 + 백그라운드 이미지 + 정보(제목)", true, true, false, true),
    TYPE_B(2, "카테고리 + 백그라운드 이미지 + 액션버튼 + 정보(제목 및 설명)", true, true, true, true),
    TYPE_C(3, "백그라운드 이미지 + 정보(제목 및 설명)", false, true, true, true),
    TYPE_D(4, "정보(제목 및 설명)", false,true, true, false);

    private Integer order;
    private String description;
    private boolean useCategory;
    private boolean useTitle;
    private boolean useSummary;
    private boolean useThumbNail;

    DatasetMainUiType(Integer order, String description, boolean useCategory, boolean useTitle, boolean useSummary, boolean useThumbNail) {
        this.order = order;
        this.description = description;
        this.useCategory = useCategory;
        this.useTitle = useTitle;
        this.useSummary = useSummary;
        this.useThumbNail = useThumbNail;
    }

    public static Map<String, Map<String, Object>> toMap() {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(DatasetMainUiType::getOrder))
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
