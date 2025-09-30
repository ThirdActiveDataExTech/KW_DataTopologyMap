package kware.apps.mobigen.cetus.tag.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagList {

    private Long tagUid;
    private String tagNm;
}
