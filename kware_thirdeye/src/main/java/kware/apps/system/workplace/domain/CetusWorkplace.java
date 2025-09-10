package kware.apps.system.workplace.domain;

import cetus.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusWorkplace extends AuditBean {

    private Long uid;
    private String name;

}
