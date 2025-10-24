package kware.apps.mobigen.integration.dto.response.relation;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RelationsFieldList {
    private String sourceField;
    private String targetField;
    private float correlation;
}
