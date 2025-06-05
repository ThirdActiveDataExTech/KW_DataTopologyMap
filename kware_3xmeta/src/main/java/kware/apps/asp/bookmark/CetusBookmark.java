package kware.apps.asp.bookmark;

import cetus.annotation.TableName;
import cetus.bean.CetusBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@TableName("cetus_bookmark")
@Getter @Setter
@NoArgsConstructor
public class CetusBookmark extends CetusBean {
    private Long userUid;
    @NotNull
    private Long contentsUid;
    // @NotNull
    // private String targetType;
    private Long regUid;
    private String regDt;
    private Long updtUid;
    private String updtDt;

    public CetusBookmark(Long userUid, Long contentsUid) {
        this.userUid = userUid;
        this.contentsUid = contentsUid;
    }

    private String title;
    private String description;
    private Long fileUid;
    private String fileId;
}
