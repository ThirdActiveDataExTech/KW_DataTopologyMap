package kware.apps.thirdeye.comment.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.comment.domain.CetusDatasetComment;
import kware.apps.thirdeye.comment.domain.CetusDatasetCommentDao;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSave;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSearch;
import kware.apps.thirdeye.comment.dto.response.DatasetCommentCount;
import kware.apps.thirdeye.comment.dto.response.DatasetCommentList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusDatasetCommentService {

    private final CetusDatasetCommentDao dao;

    @Transactional(readOnly = true)
    public List<DatasetCommentCount> findDatasetCommentCntByType(Long approvedUid) {
        return dao.getDatasetCommentCntByType(approvedUid);
    }

    @Transactional(readOnly = true)
    public Page<DatasetCommentList> findDatasetCommentPage(DatasetCommentSearch search, Pageable pageable) {
        return dao.page("getDatasetCommentPage", "getDatasetCommentPageCount", search, pageable);
    }

    @Transactional
    public void saveDatasetComment(DatasetCommentSave request) {
        CetusDatasetComment bean = new CetusDatasetComment(request);
        dao.insert(bean);
    }
}
