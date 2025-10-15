package kware.apps.mobigen.mobigen.dto.response.metadata;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     MetadataFilePreviewResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      메타데이터 파일 정보 미리보기 응답 DTO
**/

@Getter @Setter
public class MetadataFilePreviewResponse {

    private MetadataFilePreviewMetadata metadata;

    @Getter @Setter
    public class MetadataFilePreviewMetadata {
        private String metadata_id;
        private String title;
        private String issued;
        private String modified;
        private String identifier;
        private String publisher;
        private List<String> keywords;
        private String landing_page;
        private String theme;
        private String access_url;
        private String value_a;
        private String domain;
        private String ingested_at;
        private String updated_at;
    }
}
