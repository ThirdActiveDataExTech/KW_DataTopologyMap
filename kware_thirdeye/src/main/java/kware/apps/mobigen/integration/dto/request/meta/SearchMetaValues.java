package kware.apps.mobigen.integration.dto.request.meta;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchMetaValues {
    private String key;

    public SearchMetaValues(String key) {
        this.key = key;
    }
}
