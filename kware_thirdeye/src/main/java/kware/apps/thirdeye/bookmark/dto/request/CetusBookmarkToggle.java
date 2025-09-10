package kware.apps.thirdeye.bookmark.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
public class CetusBookmarkToggle {
    @NotNull
    private Long contentsUid;
    // @NotBlank
    // private String targetType; // WISH_WORK, WISH_ARTIST
}
