package kware.apps.asp.contents.domain;

import cetus.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusContents extends AuditBean {

    private String uid;
    private String title;
    private String description;
    private String contents;
    private String metadata; 
    private String ratings;
    private String sampleData;
    private String fileUid;
    private String thumbnailUid;
    private String regDt;
    private String updtDt;

}
