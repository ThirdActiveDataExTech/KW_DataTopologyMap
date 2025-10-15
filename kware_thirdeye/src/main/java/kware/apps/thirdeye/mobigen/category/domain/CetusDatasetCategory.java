package kware.apps.thirdeye.mobigen.category.domain;

import cetus.bean.AuditBean;
import kware.apps.thirdeye.mobigen.category.dto.request.SaveCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @fileName     CetusDatasetCategory
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      모비젠 데이터 목록에서 > kware 포탈 시스템으로 등록/승인 시점에 '추가 입력'되는 카테고리
**/

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
