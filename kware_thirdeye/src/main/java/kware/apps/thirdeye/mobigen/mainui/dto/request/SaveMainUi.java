package kware.apps.thirdeye.mobigen.mainui.dto.request;


import lombok.AccessLevel;
import cetus.annotation.DisplayName;
import cetus.annotation.YOrN;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMainUi {

    @NotBlank @Size(max = 64) @DisplayName("메인UI 코드")
    private String code;
    @NotBlank @Size(max = 255) @DisplayName("메인UI 이름")
    private String name;
    @Min(1) @DisplayName("최대 표시 개수")
    private Integer maxCount;
    @Min(1) @DisplayName("카테고리 최대 개수")
    private Integer categoryCnt;
    @Min(1) @DisplayName("내용 최대 줄 수")
    private Integer descLineClamp;
    @Min(1) @DisplayName("제목 최대 줄 수")
    private Integer titleLineClamp;
    @YOrN(allowNull = false) @DisplayName("썸네일 사용 여부")
    private String thumbUseAt;
    @YOrN @DisplayName("사용 여부")
    private String useAt;
    @NotBlank @Size(max = 32) @DisplayName("UI 유형")
    private String typeCd;
    private Long sortNo;
}
