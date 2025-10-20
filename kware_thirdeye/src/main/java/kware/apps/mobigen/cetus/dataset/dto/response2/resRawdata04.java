package kware.apps.mobigen.cetus.dataset.dto.response2;

import cetus.util.DateTimeUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resRawdata04 {

    private String code;
    private String message;
    private resRawdata04_result result;

    public resRawdata04(String code, String message, resRawdata04_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resRawdata04_result {
        private String rawdata_id;
        private String metadata_id;
        private String filename;
        private int file_size;
        private String format;
        private String mime_type;
        private String uploaded_at;
        private String checksum;
        private String description;
        private List<String> tags;
        private int download_count;

        public resRawdata04_result(String rawdata_id, String metadata_id) {
            this.rawdata_id = rawdata_id;
            this.metadata_id = metadata_id;

            Random random = new Random();
            int randomNum = random.nextInt(5) + 1;
            this.filename = "test_file_name_" + randomNum + ".json";
            this.file_size = random.nextInt(100) + 1;
            this.format = "json";
            this.mime_type = "application/json";
            this.uploaded_at = DateTimeUtil.getTodayShort();
            this.checksum = "checksum_" + randomNum;
            this.description = "raw data description";
            this.tags = Arrays.asList("월별", "교통량");
            this.download_count = random.nextInt(50) + 1 ;
        }
    }
}
