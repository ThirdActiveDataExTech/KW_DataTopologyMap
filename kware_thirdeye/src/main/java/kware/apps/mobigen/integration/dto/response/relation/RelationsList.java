package kware.apps.mobigen.integration.dto.response.relation;

import kware.apps.mobigen.mobigen.dto.response.relation.FieldRelationsResponse;
import kware.apps.mobigen.mobigen.dto.response.relation.RelatedMetadataResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class RelationsList {

    private String metadataId;

    private RelatedMetadataResponse relationMetadata;
    private List<FieldRelationsResponse> fieldRelations;

    public RelationsList(String metadataId) {
        this.metadataId = metadataId;
    }
}
