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

    /** 사용 중인 DATASET_COMMENT 코드 건수(0 또는 1) */
    public int countCommentType(String typeCd) {
        Integer count = selectOne("countCommentType", typeCd);
        return count == null ? 0 : count;
    }
}
