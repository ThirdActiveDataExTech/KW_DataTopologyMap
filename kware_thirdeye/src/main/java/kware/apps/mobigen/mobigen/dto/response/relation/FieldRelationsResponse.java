package kware.apps.mobigen.mobigen.dto.response.relation;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FieldRelationsResponse {
    private String source_field;
    private String target_field;
    private float correlation;
}
