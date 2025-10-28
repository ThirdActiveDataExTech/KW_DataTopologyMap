package kware.apps.mobigen.integration.dto.response.meta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter @NoArgsConstructor
public class MetaKeyValueList {

    private String filter;
    private List<String> values;
    private Map<Long, String> maps;

    public MetaKeyValueList(String filter) {
        this.filter = filter;
    }
}
