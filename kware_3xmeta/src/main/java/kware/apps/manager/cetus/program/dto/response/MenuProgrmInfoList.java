package kware.apps.manager.cetus.program.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuProgrmInfoList {

    private Long uid;
    private String progrmNm;
    private String url;
    private String useAt;

}
