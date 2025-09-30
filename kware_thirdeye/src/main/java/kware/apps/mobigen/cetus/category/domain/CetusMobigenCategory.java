package kware.apps.mobigen.cetus.category.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusMobigenCategory {

    private Long uid;
    private String categoryNm;

    public CetusMobigenCategory(String categoryNm) {
        this.categoryNm = categoryNm;
    }
}
