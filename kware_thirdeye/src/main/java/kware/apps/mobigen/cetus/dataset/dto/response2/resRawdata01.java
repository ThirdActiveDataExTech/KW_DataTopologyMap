package kware.apps.mobigen.cetus.dataset.dto.response2;


import cetus.util.DateTimeUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resRawdata01 {

    private String code;
    private String message;
    private resRawdata01_result result;

    public resRawdata01(String code, String message, resRawdata01_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resRawdata01_result {
        private String metadata_id;
        private int total_count;
        private List<resRawdata01_items> items;

        public resRawdata01_result(String metadata_id, int total_count, List<resRawdata01_items> items) {
            this.metadata_id = metadata_id;
            this.total_count = total_count;
            this.items = items;
        }

        @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class resRawdata01_items {
            private String rawdata_id;
            private String filename;
            private int file_size;
            private String format;
            private String updated_at;
            private String checksum;

            public resRawdata01_items(String checksum) {
                Random random = new Random();
                int randomNum = random.nextInt(5) + 1;
                this.rawdata_id = "rawdata_" + randomNum;
                this.filename = "test_file_name_" + rawdata_id + ".json";
                this.file_size = random.nextInt(100) + 1;
                this.format = "json";
                this.updated_at = DateTimeUtil.getTodayShort();
                this.checksum = checksum;
            }
        }
    }
}
