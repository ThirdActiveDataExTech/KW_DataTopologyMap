package kware.apps.mobigen.dto.response.pckge;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PackagePreview {

    private int file_count;
    private List<Preview> preview_data;

    @Getter @Setter
    public static class Preview {
        private String filename;
        private Metadata metadata;

        @Getter @Setter
        public static class Metadata {
            private String title;
            private String publisher;
            private String format;
        }
    }
}
