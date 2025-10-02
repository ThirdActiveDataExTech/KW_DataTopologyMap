package kware.apps.mobigen.cetus.dataset.dto.response;


import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import kware.common.file.domain.CommonFile;
import kware.common.file.domain.CommonFileDao;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobigenDatasetView {

    private Long uid;
    private String title;
    private String registrantId;
    private String regDt;
    private Long metadataFileUid;
    private List<CommonFile> metadataFiles;
    private Long realdataFileUid;
    private List<CommonFile> realdataFiles;
    private String metadata;
    private List<TagList> tags;
    private List<CategoryList> categories;

    public void setTags(List<TagList> tags) {
        this.tags = tags;
    }

    public void setCategories(List<CategoryList> categories) {
        this.categories = categories;
    }

    public void setMetadataFiles(List<CommonFile> metadataFiles) {
        this.metadataFiles = metadataFiles;
    }

    public void setRealdataFiles(List<CommonFile> realdataFiles) {
        this.realdataFiles = realdataFiles;
    }
}
