package kware.apps.mobigen.mobigen.dto.request.metadata.create;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Getter @Setter @Slf4j
public class CreateMetadataFieldRequest {
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

    public CreateMetadataFieldRequest() {}

    public CreateMetadataFieldRequest(String jsonValue) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // JSON에는 있지만 클래스 필드에는 없는 항목이 있어도 에러 내지 않음
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // JSON String을 현재 객체 타입으로 읽어서 필드 복사
            CreateMetadataFieldRequest temp = objectMapper.readValue(jsonValue, CreateMetadataFieldRequest.class);

            this.title = temp.title;
            this.issued = temp.issued;
            this.modified = temp.modified;
            this.description = temp.description;
            this.publisher = temp.publisher;
            this.keywords = temp.keywords;
            this.landing_page = temp.landing_page;
            this.theme = temp.theme;
            this.access_url = temp.access_url;
            this.custom_meta = temp.custom_meta;

        } catch (Exception e) {
            log.error("JSON 파싱 오류: ", e);
        }
    }
}
