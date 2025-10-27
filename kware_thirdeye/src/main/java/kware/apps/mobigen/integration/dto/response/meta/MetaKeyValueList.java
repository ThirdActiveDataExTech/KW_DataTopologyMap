package kware.apps.mobigen.integration.dto.response.meta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class MetaKeyValueList {

    private String filter;
    private List<String> values;

    public MetaKeyValueList(String filter, List<String> values) {
        this.filter = filter;
        this.values = values;
    }
}
