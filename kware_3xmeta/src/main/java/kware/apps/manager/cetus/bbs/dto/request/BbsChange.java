package kware.apps.manager.cetus.bbs.dto.request;

import cetus.annotation.DisplayName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BbsChange {

    @NotBlank @DisplayName("게시판 이름")
    private String bbsNm;

    @NotBlank @DisplayName("게시판 유형")
    private String bbsTpCd;

    @NotBlank @DisplayName("사용 여부")
    private String useAt;
}
