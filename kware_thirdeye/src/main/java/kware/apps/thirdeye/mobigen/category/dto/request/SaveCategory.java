package kware.apps.thirdeye.mobigen.category.dto.request;


import lombok.AccessLevel;
import cetus.annotation.DisplayName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveCategory {

    private Long uid;                       // 기존 카테고리면 uid, 새 카테고리면 null + 이름
    @NotBlank @Size(max = 255) @DisplayName("카테고리 이름")
    private String categoryNm;
}
