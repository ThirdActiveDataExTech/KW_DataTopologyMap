package kware.apps.manager.cetus.program.dto.request;

import cetus.annotation.DisplayName;
import cetus.annotation.YOrN;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramSave {

    @NotBlank @DisplayName("프로그램명")
    private String progrmNm;

    @NotBlank @DisplayName("URL")
    private String url;

    @YOrN
    private String useAt;

    private String progrmDc;
}
