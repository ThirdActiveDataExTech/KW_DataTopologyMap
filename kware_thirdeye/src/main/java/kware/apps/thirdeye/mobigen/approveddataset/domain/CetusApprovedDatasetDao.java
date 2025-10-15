package kware.apps.thirdeye.mobigen.approveddataset.domain;

import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.ApprovedDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.HomeDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetIdList;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetView;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetList;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.HomeDatasetList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusApprovedDatasetDao extends SuperDao<CetusApprovedDataset> {

    public CetusApprovedDatasetDao() {
        super("cetusApprovedDataset");
    }

    public List<ApprovedDatasetIdList> getApprovedDatasetIdList(Long workplaceUid) {
        return selectList("getApprovedDatasetIdList", workplaceUid);
    }

    public ApprovedDatasetView getApprovedDatasetView(Long uid) {
        return selectOne("getApprovedDatasetView", uid);
    }

    public List<ApprovedDatasetList> getDatasetList(ApprovedDatasetSearch search) {
        return selectList("getDatasetList", search);
    }

    public void deleteApprovedDataset(CetusApprovedDataset bean) {
        update("deleteApprovedDataset", bean);
    }

    public List<HomeDatasetList> getHomeDatasetList(HomeDatasetSearch search){
        return selectList("getHomeDatasetList", search);
    }
}
