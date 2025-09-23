package kware.apps.mobigen.dto.response.metadata;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateMetadataResponse {

    private String metadata_id;
    private String title;
    private String issued;
    private String modified;
    private String identifier;
    private String publisher;
    private String keyword;
    private String landing_page;
    private String theme;
    private String access_url;
    private String ingested_at;
    private String updated_at;
}
