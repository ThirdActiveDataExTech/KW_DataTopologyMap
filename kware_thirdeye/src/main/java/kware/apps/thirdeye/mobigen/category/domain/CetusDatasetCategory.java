package kware.apps.thirdeye.mobigen.category.domain;

import cetus.bean.AuditBean;
import kware.apps.thirdeye.mobigen.category.dto.request.SaveCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDatasetCategory extends AuditBean {

    private Long uid;
    private Long workplaceUid;
    private String categoryNm;

    public CetusDatasetCategory(SaveCategory request, Long workplaceUid) {
        this.workplaceUid = workplaceUid;
        this.categoryNm = request.getCategoryNm();
    }
}
