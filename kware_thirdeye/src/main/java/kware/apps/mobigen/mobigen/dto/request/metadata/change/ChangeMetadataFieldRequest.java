package kware.apps.mobigen.mobigen.dto.request.metadata.change;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
public class ChangeMetadataFieldRequest {
    private String title;
    private String issued;
    private String modified;
    private String identifier;
    private String publisher;
    private List<String> keyword;
    private String landing_page;
    private List<String> theme;
    private String access_url;

    public ChangeMetadataFieldRequest() {}

    public ChangeMetadataFieldRequest(String jsonValue) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON에는 있지만 클래스 필드에는 없는 항목이 있어도 에러 내지 않음
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // JSON String을 현재 객체 타입으로 읽어서 필드 복사
            ChangeMetadataFieldRequest temp = objectMapper.readValue(jsonValue, ChangeMetadataFieldRequest.class);

            this.title = temp.title;
            this.issued = temp.issued;
            this.modified = temp.modified;
            this.publisher = temp.publisher;
            this.keyword = temp.keyword;
            this.landing_page = temp.landing_page;
            this.theme = temp.theme;
            this.access_url = temp.access_url;

        } catch (Exception e) {
            // 로깅 후 필요에 따라 예외 처리
            log.error("JSON 파싱 오류: ", e);
        }
    }
}
