package kware.apps.mobigen.mobigen.dto.response.metadata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     MetadataList
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      필터링된 메타데이터 목록 전달
**/

@Getter @Setter
public class MetadataList {

    private int total_count;
    private int page;
    private int limit;
    private List<Items> items;

    @Getter @Setter
    public static class Items {
        private String metadata_id;
        private String title;
        private String issued;
        private String modified;
        private String identifier;
        private String publisher;
        private String keyword;
        private String landing_page;
        private String theme;
        private String access_url;
        private String ingested_at;
        private String updated_at;
    }
}
