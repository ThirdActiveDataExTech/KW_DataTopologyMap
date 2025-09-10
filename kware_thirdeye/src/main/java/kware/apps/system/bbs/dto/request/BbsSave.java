package kware.apps.system.bbs.dto.request;

import cetus.annotation.DisplayName;
import cetus.annotation.YOrN;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BbsSave {

    @NotBlank @DisplayName("게시판 이름")
    private String bbsNm;

    @NotBlank @DisplayName("게시판 유형")
    private String bbsTpCd;

    @YOrN
    private String useAt;

}
