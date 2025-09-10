package kware.apps.thirdeye.contents.dto.request;

import kware.common.file.domain.CommonFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentChange {

    private String title;
    private String description;
    private String contents;
    
    private String metadata;
    
    private String sampleData;

    private List<CommonFile> contentFile;
    private List<CommonFile> contentFileDel;
    private Long contentFileUid;

    private List<CommonFile> thumbnail;
    private List<CommonFile> thumbnailDel;
    private Long thumbnailUid;

}
