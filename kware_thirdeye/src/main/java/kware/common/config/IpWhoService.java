package kware.common.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class IpWhoService {

    public Map<String, Object> getGeoInfo(String ip) {
        String url = "https://ipwho.is/" + ip;
        // 매번 new RestTemplate()을 하는 것보다 Bean 주입을 권장하지만,
        // 우선 예외 처리부터 적용합니다.
        RestTemplate restTemplate = new RestTemplate();
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
