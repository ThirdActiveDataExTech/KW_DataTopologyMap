package kware.apps.superrole.dto.request.createworkplace;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateProgram {
    private Long programUid;
    private String programNm;
    private String programUrl;
    private String isRootUrl;
}
