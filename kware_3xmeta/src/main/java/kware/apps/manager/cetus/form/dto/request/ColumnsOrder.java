package kware.apps.manager.cetus.form.dto.request;

import cetus.user.UserUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColumnsOrder {
    @NotNull
    private Long uid;
    @NotNull
    private Integer sortNum;
    @NotBlank
    private String direction;
    @NotBlank
    private String formGroup;

    private Long workplaceUid = UserUtil.getUser().getWorkplaceUid();
}
