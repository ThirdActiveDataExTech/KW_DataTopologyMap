package kware.apps.mobigen.mobigen.dto.request.rawdata.change;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter @Setter @Slf4j
public class ChangeRawdataFieldRequest {
    private String description;
    private List<String> tags;

    public ChangeRawdataFieldRequest() {}

    public ChangeRawdataFieldRequest(String jsonValue) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON에는 있지만 클래스 필드에는 없는 항목이 있어도 에러 내지 않음
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // JSON String을 현재 객체 타입으로 읽어서 필드 복사
            ChangeRawdataFieldRequest temp = objectMapper.readValue(jsonValue, ChangeRawdataFieldRequest.class);
            this.description = temp.description;
            this.tags = temp.tags;
        } catch (Exception e) {
            // 로깅 후 필요에 따라 예외 처리
            log.error("JSON 파싱 오류: ", e);
        }
    }
}
