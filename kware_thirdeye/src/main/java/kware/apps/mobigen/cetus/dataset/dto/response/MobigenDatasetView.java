package kware.apps.mobigen.cetus.dataset.dto.response;


import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
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

    private List<TagList> tags;                 // 태그 목록

    private CetusDatasetFileView metadataFile;        // 메타데이터 파일
    private List<CetusDatasetFileView> rawdataFiles;  // 원본데이터 파일 목록

    public void setTags(List<TagList> tags) {
        this.tags = tags;
    }

    public void setMetadataFile(CetusDatasetFileView metadataFile) {
        this.metadataFile = metadataFile;
    }
    public void setRawdataFiles(List<CetusDatasetFileView> rawdataFiles) {
        this.rawdataFiles = rawdataFiles;
    }

    public void setRegistrantId(String registrantId) {
        this.registrantId = registrantId;
    }
}
