package kware.apps.thirdeye.mobigen.thirdeyeregistrant.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThirdeyeRegistrantView {

    private Long registrantUid;
    private String registrantId;
    private boolean isRegistered;
}
