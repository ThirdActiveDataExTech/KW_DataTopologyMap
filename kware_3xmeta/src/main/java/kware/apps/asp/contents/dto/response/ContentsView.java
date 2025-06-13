package kware.apps.asp.contents.dto.response;

import java.util.List;

import kware.apps.asp.contents.domain.CetusContents;
import kware.apps.asp.contents.domain.CetusTags;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentsView {

    private Long uid;
    private String title;
    private String description;
    private String contents;
    private String metadata; 
    private String ratings;
    private String sampleData;
    private Long fileUid;
    private String fileId;
    private Long thumbnailUid;
    private String thumbnailId;
    private String regDt;
    private String updtDt;

    private List<CetusTags> tags;
}
