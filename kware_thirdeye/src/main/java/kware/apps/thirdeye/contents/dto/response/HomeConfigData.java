package kware.apps.thirdeye.contents.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HomeConfigData {

    private String type;
    private String maxCount;
    private String listUrl;
}
