package kware.apps.mobigen.integration.dto.response.meta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class MetaKeyList {
    private List<String> filters;

    public MetaKeyList(List<String> filters) {
        this.filters = filters;
    }
}
