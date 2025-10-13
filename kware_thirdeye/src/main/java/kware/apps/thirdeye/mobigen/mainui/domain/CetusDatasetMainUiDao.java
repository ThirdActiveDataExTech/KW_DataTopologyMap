package kware.apps.thirdeye.mobigen.mainui.domain;


import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.mainui.dto.request.SearchDuplicateCode;
import kware.apps.thirdeye.mobigen.mainui.dto.response.MainUiList;
import kware.apps.thirdeye.mobigen.mainui.dto.response.MainUiView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusDatasetMainUiDao extends SuperDao<CetusDatasetMainUi> {

    public CetusDatasetMainUiDao() {
        super("cetusDatasetMainUi");
    }

    public Integer getCountByCode(SearchDuplicateCode search) {
        return selectOne("getCountByCode", search);
    }

    public MainUiView getDatasetMainUiByUid(Long uid) {
        return selectOne("getDatasetMainUiByUid", uid);
    }

    public void updateDatasetMainUidDeleteAt(CetusDatasetMainUi bean) {
        update("updateDatasetMainUidDeleteAt", bean);
    }

    public List<MainUiList> getDatasetMainUiList(Long workplaceUid) {
        return selectList("getDatasetMainUiList", workplaceUid);
    }
}
