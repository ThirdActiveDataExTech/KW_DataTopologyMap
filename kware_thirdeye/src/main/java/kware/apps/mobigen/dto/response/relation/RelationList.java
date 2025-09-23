package kware.apps.mobigen.dto.response.relation;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     RelationList
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      메타데이터의 연관 데이터 조회
**/

@Getter @Setter
public class RelationList {

    private String metadata_id;
    private List<RelatedMetadata> related_metadata;
    private List<FieldRelations> field_relations;

    @Getter @Setter
    public static class RelatedMetadata {
        private String metadata_id;
        private String title;
        private float similarity_score;
        private List<String> common_fields;
    }

    @Getter @Setter
    public static class FieldRelations {
        private String source_field;
        private String target_field;
        private float correlation;
    }
}
