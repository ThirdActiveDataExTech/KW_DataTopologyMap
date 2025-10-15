package kware.apps.mobigen.cetus.dataset.dto.response;


import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import kware.common.file.domain.CommonFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
* @fileName     MobigenDatasetView
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      모비젠 데이터셋 상세 정보 조회 응답 dto
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobigenDatasetView {

    private Long datasetId;                     // 데이터셋 ID
    private String title;                       // 데이터셋 제목
    private String regDt;                       // 등록일
    private String metadata;                    // 메타데이터

    private String registrantId;                // 등록자 ID

    private Long metadataFileUid;               // 메타데이터 파일 UID
    private List<CommonFile> metadataFiles;     // 메타데이터 파일 목록

    private Long realdataFileUid;               // 실(원본)데이터 파일 UID
    private List<CommonFile> realdataFiles;     // 실(원본)데이터 파일 목록

    private List<TagList> tags;                 // 태그 목록

    public void setTags(List<TagList> tags) {
        this.tags = tags;
    }

    public void setMetadataFiles(List<CommonFile> metadataFiles) {
        this.metadataFiles = metadataFiles;
    }
    public void setRealdataFiles(List<CommonFile> realdataFiles) {
        this.realdataFiles = realdataFiles;
    }

    public void setRegistrantId(String registrantId) {
        this.registrantId = registrantId;
    }
}
