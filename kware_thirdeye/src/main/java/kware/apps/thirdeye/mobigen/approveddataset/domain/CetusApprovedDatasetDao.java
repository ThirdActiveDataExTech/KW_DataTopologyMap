package kware.apps.thirdeye.mobigen.approveddataset.domain;

import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.HomeDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SearchApprovedDatasetView;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SearchMetadataApproved;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetView;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.HomeDatasetList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusApprovedDatasetDao extends SuperDao<CetusApprovedDataset> {

    public CetusApprovedDatasetDao() {
        super("cetusApprovedDataset");
    }

    public Boolean getMetadataIsApproved(SearchMetadataApproved search) {
        return selectOne("getMetadataIsApproved", search);
    }

    public ApprovedDatasetView getApprovedDatasetView(SearchApprovedDatasetView search) {
        return selectOne("getApprovedDatasetView", search);
    }

    public void deleteApprovedDataset(CetusApprovedDataset bean) {
        update("deleteApprovedDataset", bean);
    }

    public List<HomeDatasetList> getHomeDatasetList(HomeDatasetSearch search){
        return selectList("getHomeDatasetList", search);
    }

    public void updateApprovedDatasetSearchData(CetusApprovedDataset bean) {
        update("updateApprovedDatasetSearchData", bean);
    }

}
