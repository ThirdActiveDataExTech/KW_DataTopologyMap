package cetus.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ObjectUtil {

    public static String makeJsonString( Object map ) {
        String stringJson = "{}";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            stringJson = objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            log.error("err: ", e);
            stringJson = "{}";
        }
        return stringJson;
    }
}
