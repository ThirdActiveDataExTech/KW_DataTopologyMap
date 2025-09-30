package kware.apps.mobigen.cetus.tag.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusMobigenTag {

    private Long uid;
    private String tagNm;

    public CetusMobigenTag(String tagNm) {
        this.tagNm = tagNm;
    }
}
