package kware.apps.thirdeye.mobigen.datasetui.domain;


import java.util.Collections;
import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiGroup;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CetusDatasetUiDao extends SuperDao<CetusDatasetUi> {

    public CetusDatasetUiDao() {
        super("cetusDatasetUi");
    }

    public Integer getCountDatasetMainUiUse(Long uid) {
        return selectOne("getCountDatasetMainUiUse", uid);
    }

    public DatasetUiView getDatasetUiView(Long approvedDatasetUid) {
        return selectOne("getDatasetUiView", approvedDatasetUid);
    }

    /** 여러 진열 데이터셋의 진열 정보를 한 번에 조회한다(목록 페이지용) */
    public List<DatasetUiView> getDatasetUiViewList(List<Long> approvedDatasetUids) {
        return selectList("getDatasetUiViewList", Collections.singletonMap("approvedDatasetUids", approvedDatasetUids));
    }

    public List<DatasetUiGroup> getDatasetUiByGroup(Long workplaceUid) {
        return selectList("getDatasetUiByGroup", workplaceUid);
    }

    public void updateDatasetUiShowAt(CetusDatasetUi bean) {
        update("updateDatasetUiShowAt", bean);
    }

    public void updateDatasetCategory(CetusDatasetUi bean) {
        update("updateDatasetCategory", bean);
    }

    /** extra_json 의 title 만 채운다(다른 키는 보존). 제목 백필용 */
    public int updateExtraJsonTitle(Long approvedDatasetUid, String title) {
        Map<String, Object> param = new HashMap<>();
        param.put("approvedDatasetUid", approvedDatasetUid);
        param.put("title", title);
        return update("updateExtraJsonTitle", param);
    }
}
