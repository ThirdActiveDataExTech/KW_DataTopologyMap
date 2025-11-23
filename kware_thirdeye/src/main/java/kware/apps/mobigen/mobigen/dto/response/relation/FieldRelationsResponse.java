package kware.apps.mobigen.mobigen.dto.response.relation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class FieldRelationsResponse {
    private String source_field;
    private String target_field;
    private float correlation;
}
