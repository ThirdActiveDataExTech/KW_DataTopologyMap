package kware.apps.mobigen.dto.response.metadata;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteMetadataResponse {

    private String metadata_id;
    private String deleted_at;

}
