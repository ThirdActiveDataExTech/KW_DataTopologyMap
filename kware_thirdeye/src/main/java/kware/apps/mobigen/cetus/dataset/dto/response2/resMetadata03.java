package kware.apps.mobigen.cetus.dataset.dto.response2;


import cetus.util.DateTimeUtil;
import kware.apps.mobigen.cetus.dataset.dto.request2.reqMetadata03;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resMetadata03 {

    private String code;
    private String message;
    private resMetadata03_result result;

    public resMetadata03(String code, String message, resMetadata03_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resMetadata03_result {

        private resMetadata03_metadata metadata;
        private resMetadata03_rawdata rawdata;

        public resMetadata03_result(reqMetadata03 request) {
            this.metadata = new resMetadata03_metadata(request.getField().getTitle(), request.getField().getKeywords());
        }

        public resMetadata03_result(reqMetadata03 request, MultipartFile file) {
            this.metadata = new resMetadata03_metadata(request.getField().getTitle(), request.getField().getKeywords());
            this.rawdata = new resMetadata03_rawdata(file.getOriginalFilename(), Long.toString(file.getSize()));
        }

        @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public class resMetadata03_metadata {
            private String metadata_id;
            private String title;
            private String issued;
            private String modified;
            private String identifier;
            private String publisher;
            private String keyword;
            private List<String> keywords;
            private String landing_page;
            private String theme;
            private String access_url;
            private String value_a;
            private String domain;
            private String ingested_at;
            private String updated_at;

            public resMetadata03_metadata(String title, List<String> keywords) {
                Random random = new Random();
                int randomNum = random.nextInt(5) + 1;
                this.metadata_id = "metadata_" + randomNum;
                this.title = title;
                this.keywords = keywords;
                this.ingested_at = DateTimeUtil.getTodayShort();
                this.updated_at = DateTimeUtil.getTodayShort();
            }
        }

        @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public class resMetadata03_rawdata {
            private String rawdata_id;
            private String filename;
            private String size;
            private String uploaded_at;

            public resMetadata03_rawdata(String filename, String size) {
                Random random = new Random();
                int randomNum = random.nextInt(5) + 1;
                this.rawdata_id = "rawdata_" + randomNum;
                this.filename = filename;
                this.size = size;
                this.uploaded_at = DateTimeUtil.getTodayShort();
            }
        }
    }
}
