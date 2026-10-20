package kware.common.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@lombok.RequiredArgsConstructor
public class IpWhoService {

    // 타임아웃이 설정된 공용 RestTemplate(RestTemplateConfig). 로그인 성공 처리 중 호출되므로 지연이 로그인 응답을 막지 않게 한다.
    private final RestTemplate restTemplate;

    public Map<String, Object> getGeoInfo(String ip) {
        String url = "https://ipwho.is/" + ip;
        try {
            // API 호출 및 결과 반환
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            // 핵심: 에러가 발생해도 로그만 남기고 빈 Map을 반환하여
            // 이후 로그인 저장 로직이 깨지지 않게 합니다.
            log.error(">>>> [IpWhoService] 외부 API 호출 중 에러 발생 (IP: {}), 사유: {}", ip, e.getMessage());
            return new HashMap<>();
        }
    }
}
