package kware.apps.thirdeye.mobigen.approveddataset.domain;

import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.HomeDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetItem;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.HomeDatasetList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CetusApprovedDatasetDao extends SuperDao<CetusApprovedDataset> {

    public CetusApprovedDatasetDao() {
        super("cetusApprovedDataset");
    }

    public Boolean getMetadataIsApproved(Map<String, Object> map) {
        return selectOne("getMetadataIsApproved", map);
    }

    public ApprovedDatasetItem getApprovedDatasetView(Map<String, Object> map) {
        return selectOne("getApprovedDatasetView", map);
    }

    public void deleteApprovedDataset(CetusApprovedDataset bean) {
        update("deleteApprovedDataset", bean);
    }

    public List<ApprovedDatasetItem> getHomeDatasetList(HomeDatasetSearch search){
        return selectList("getHomeDatasetList", search);
    }

    public void updateApprovedDatasetSearchData(CetusApprovedDataset bean) {
        update("updateApprovedDatasetSearchData", bean);
    }

    /** 진열 중인데 extra_json 에 제목이 없는 데이터셋(제목 백필 대상) */
    public List<ApprovedDatasetItem> getTitleMissingList() {
        return selectList("getTitleMissingList");
    }

}
