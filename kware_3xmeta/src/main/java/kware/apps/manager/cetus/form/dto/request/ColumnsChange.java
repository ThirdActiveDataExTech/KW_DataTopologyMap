package kware.apps.manager.cetus.form.dto.request;

import cetus.annotation.DisplayName;
import kware.apps.manager.cetus.form.domain.CetusColumnOptions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColumnsChange {

    private Long workplaceUid;

    @NotBlank @DisplayName("타입")
    private String type;
    @NotBlank @DisplayName("라벨")
    private String label;
    @NotNull
    @DisplayName("필수")
    private boolean required;
    private String description;
    private String useAt;
    @NotBlank @DisplayName("이름")
    private String name;
    @NotBlank @DisplayName("placeholder")
    private String placeholder;

    private List<CetusColumnOptions> options = new ArrayList<>();
}
