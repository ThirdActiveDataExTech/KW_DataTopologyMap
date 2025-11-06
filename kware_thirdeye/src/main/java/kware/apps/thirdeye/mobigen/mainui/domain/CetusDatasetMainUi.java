package kware.apps.thirdeye.mobigen.mainui.domain;


import cetus.bean.AuditBean;
import kware.apps.thirdeye.mobigen.mainui.dto.request.ChangeMainUi;
import kware.apps.thirdeye.mobigen.mainui.dto.request.SaveMainUi;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDatasetMainUi extends AuditBean {

    private Long uid;
    private String code;
    private String name;
    private Integer maxCount;
    private Integer categoryCnt;
    private Integer descLineClamp;
    private Integer titleLineClamp;
    private String thumbUseAt;
    private Long workplaceUid;
    private String useAt;
    private String deleteAt;
    private String typeCd;
    private Long sortNo;

    public CetusDatasetMainUi(SaveMainUi request, Long workplaceUid) {
        this.code = request.getCode();
        this.name = request.getName();
        this.maxCount = request.getMaxCount();
        this.categoryCnt = request.getCategoryCnt();
        this.descLineClamp = request.getDescLineClamp();
        this.titleLineClamp = request.getTitleLineClamp();
        this.thumbUseAt = request.getThumbUseAt();
        this.useAt = request.getUseAt();
        this.typeCd = request.getTypeCd();
        this.sortNo = request.getSortNo();
        this.workplaceUid = workplaceUid;
    }

    public CetusDatasetMainUi(Long uid) {
        this.uid = uid;
    }

    public CetusDatasetMainUi(Long uid, ChangeMainUi request) {
        this.name = request.getName();
        this.maxCount = request.getMaxCount();
        this.categoryCnt = request.getCategoryCnt();
        this.descLineClamp = request.getDescLineClamp();
        this.titleLineClamp = request.getTitleLineClamp();
        this.thumbUseAt = request.getThumbUseAt();
        this.useAt = request.getUseAt();
        this.sortNo = request.getSortNo();
        this.uid = uid;
    }
}
