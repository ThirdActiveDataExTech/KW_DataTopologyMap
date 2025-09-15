package kware.apps.thirdeye.comment.domain;


import cetus.dao.SuperDao;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSearch;
import org.springframework.stereotype.Component;

@Component
public class CetusDatasetCommentDao extends SuperDao<CetusDatasetComment> {

    public CetusDatasetCommentDao() {
        super("cetusDatasetComment");
    }

    public Integer getDatasetCommentCntByType(DatasetCommentSearch search) {
        return selectOne("getDatasetCommentCntByType", search);
    }
}
