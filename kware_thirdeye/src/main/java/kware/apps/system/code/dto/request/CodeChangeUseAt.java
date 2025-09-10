package kware.apps.system.code.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeChangeUseAt {

    private Long uid;
    private String useAt;
}
