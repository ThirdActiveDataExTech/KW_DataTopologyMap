package kware.apps.mobigen.cetus.dataset.dto.response2;


import cetus.util.DateTimeUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@Getter  @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resRawdata02 {
    private String code;
    private String message;
    private resRawdata02_result result;

    public resRawdata02(String code, String message, resRawdata02_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resRawdata02_result {
        private String rawdata_id;
        private String filename;
        private String file_size;
        private String uploaded_at;

        public resRawdata02_result(MultipartFile file) {
            Random random = new Random();
            int randomNum = random.nextInt(5) + 1;
            this.rawdata_id = "rawdata_" + randomNum;
            this.filename = file.getOriginalFilename();
            this.file_size = Long.toString(file.getSize());
            this.uploaded_at = DateTimeUtil.getTodayShort();
        }
    }
}
