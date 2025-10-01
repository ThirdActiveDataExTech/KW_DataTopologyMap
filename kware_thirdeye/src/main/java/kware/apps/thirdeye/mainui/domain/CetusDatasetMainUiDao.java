package kware.apps.thirdeye.mainui.domain;


import cetus.dao.SuperDao;
import kware.apps.thirdeye.mainui.dto.request.SearchDuplicateCode;
import kware.apps.thirdeye.mainui.dto.response.MainUiView;
import org.springframework.stereotype.Component;

@Component
public class CetusDatasetMainUiDao extends SuperDao<CetusDatasetMainUi> {

    public CetusDatasetMainUiDao() {
        super("cetusDatasetMainUi");
    }

    public Integer getCountByCode(SearchDuplicateCode search) {
        return selectOne("getCountByCode", search);
    }

    public Integer getCountDatasetMainUiUse(Long uid) {
        return selectOne("getCountDatasetMainUiUse", uid);
    }

    public MainUiView getDatasetMainUiByUid(Long uid) {
        return selectOne("getDatasetMainUiByUid", uid);
    }

    public void updateDatasetMainUidDeleteAt(CetusDatasetMainUi bean) {
        update("updateDatasetMainUidDeleteAt", bean);
    }
}
