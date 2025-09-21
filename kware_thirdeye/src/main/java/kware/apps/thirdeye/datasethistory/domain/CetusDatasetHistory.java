package kware.apps.thirdeye.datasethistory.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.web.PortResolverImpl;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDatasetHistory {

    private Long historyUid;
    private Long approvedDatasetUid;
    private String statusCd;            // SHOW, HIDE 코드값
    private String chngDt;
    private String chngUid;

}
