package kware.apps.thirdeye.mobigen.datasetui.dto.request;

import cetus.bean.FileBean;
import kware.apps.thirdeye.mobigen.category.dto.request.SaveCategory;
import lombok.AccessLevel;
import cetus.annotation.DisplayName;
import cetus.annotation.YOrN;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeDatasetUi extends FileBean {

    @NotNull @DisplayName("메인 UI")
    private Long mainUiUid;
    @YOrN(allowNull = false) @DisplayName("공개 여부")
    private String showAt;
    @NotNull @DisplayName("정렬 순서")
    private Long sortNo;
    private Long thumbUid;
    private String extraJson;
    @NotNull @Valid @DisplayName("카테고리")
    private SaveCategory category;

}
