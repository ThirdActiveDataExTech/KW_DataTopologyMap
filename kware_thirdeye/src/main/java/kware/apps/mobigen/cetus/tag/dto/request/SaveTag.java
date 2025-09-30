package kware.apps.mobigen.cetus.tag.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveTag {

    private Long uid;
    private String tagNm;
}
