package kware.apps.mobigen.mobigen.dto.response.pckge;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     PackagePreview
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      패키지 파일의 내용을 json형태로 변경
**/

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
