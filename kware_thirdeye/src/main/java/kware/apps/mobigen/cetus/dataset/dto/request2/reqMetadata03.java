package kware.apps.mobigen.cetus.dataset.dto.request2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
public class reqMetadata03 {

    private final String action = "create";
    private reqMetadata03_field field;

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
    public static class reqMetadata03_field {
        private String title;
        private String issued;
        private String modified;
        private String description;
        private String publisher;
        private List<String> keywords;
        private String landing_page;
        private List<String> theme;
        private String access_url;
        private Map<String, Object> custom_meta;
    }
}
