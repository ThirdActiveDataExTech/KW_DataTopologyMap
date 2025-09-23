package kware.apps.mobigen.dto.response.relation;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
