package kware.apps.mobigen.integration.dto.request.metadata;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SearchMetadataView {

    private String metadataId;
    public SearchMetadataView(String metadataId) {
        this.metadataId = metadataId;
    }
}
