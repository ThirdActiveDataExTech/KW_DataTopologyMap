package kware.apps.thirdeye.approveddataset.domain;

import cetus.dao.SuperDao;
import kware.apps.thirdeye.approveddataset.dto.request.DatasetViewSearch;
import kware.apps.thirdeye.approveddataset.dto.response.ApprovedDatasetView;
import kware.apps.thirdeye.approveddataset.dto.response.DatasetDetailView;
import kware.apps.thirdeye.approveddataset.dto.response.DatasetIdList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusDatasetDao extends SuperDao<CetusDataset> {

    public CetusDatasetDao() {
        super("cetusDataset");
    }

    public DatasetDetailView getDatasetByUid(DatasetViewSearch search) {
        return selectOne("getDatasetByUid", search);
    }

    public List<DatasetIdList> getApprovedDatasetIdList(Long workplaceUid) {
        return selectList("getApprovedDatasetIdList", workplaceUid);
    }

    public ApprovedDatasetView getApprovedDatasetView(Long uid) {
        return selectOne("getApprovedDatasetView", uid);
    }
}
