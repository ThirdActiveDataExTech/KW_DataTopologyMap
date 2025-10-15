package kware.apps.thirdeye.mobigen.mobigenregistrant.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobigenRegistrantView {

    private Long registrantUid;
    private String registrantId;
    private boolean isRegistered;
}
