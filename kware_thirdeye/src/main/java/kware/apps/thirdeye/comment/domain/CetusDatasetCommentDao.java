package kware.apps.thirdeye.comment.domain;


import cetus.dao.SuperDao;
import kware.apps.thirdeye.comment.dto.response.DatasetCommentCount;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusDatasetCommentDao extends SuperDao<CetusDatasetComment> {

    public CetusDatasetCommentDao() {
        super("cetusDatasetComment");
    }

    public List<DatasetCommentCount> getDatasetCommentCntByType(Long approvedUid) {
        return selectList("getDatasetCommentCntByType", approvedUid);
    }
}
