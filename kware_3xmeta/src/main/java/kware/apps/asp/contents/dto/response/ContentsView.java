package kware.apps.asp.contents.dto.response;

import kware.apps.asp.contents.domain.CetusTags;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentsView {

    private Long uid;
    private Long workplaceUid;
    private String title;
    private String description;
    private String contents;
    private String metadata;
    private Integer ratings;
    private String bookmark;
    private String sampleData;
    private Long fileUid;
    private String fileId;
    private String filePath;
    private String fileType;
    private String fileName;
    private Long thumbnailUid;
    private String thumbnailId;
    private String regDt;
    private String updtDt;
    private String regUid;
    private String updtUid;

    private List<CetusTags> tags;
}
