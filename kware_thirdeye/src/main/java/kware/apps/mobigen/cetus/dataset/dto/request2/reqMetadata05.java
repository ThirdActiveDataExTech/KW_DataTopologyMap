package kware.apps.mobigen.cetus.dataset.dto.request2;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
public class reqMetadata05 {

    private final String action = "update";
    private String metadata_id;
    private reqMetadata05_field field;

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
    public static class reqMetadata05_field {
        private String title;
        private String issued;
        private String modified;
        private String identifier;
        private String publisher;
        private String keyword;
        private List<String> keywords;
        private String landing_page;
        private List<String> theme;
        private String access_url;
    }
}
