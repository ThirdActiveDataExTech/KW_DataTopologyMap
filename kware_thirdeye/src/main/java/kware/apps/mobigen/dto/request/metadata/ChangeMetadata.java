package kware.apps.mobigen.dto.request.metadata;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class ChangeMetadata {

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
    private Map<String, Object> custom_data;

}
