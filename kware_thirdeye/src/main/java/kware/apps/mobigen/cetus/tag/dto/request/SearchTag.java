package kware.apps.mobigen.cetus.tag.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchTag {

    private String tagNm;

    public SearchTag(String tagNm) {
        this.tagNm = tagNm;
    }
}
