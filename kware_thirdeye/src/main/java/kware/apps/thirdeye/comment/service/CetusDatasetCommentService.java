package kware.apps.thirdeye.comment.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.comment.domain.CetusDatasetComment;
import kware.apps.thirdeye.comment.domain.CetusDatasetCommentDao;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSave;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSearch;
import kware.apps.thirdeye.comment.dto.response.DatasetCommentCount;
import kware.apps.thirdeye.comment.dto.response.DatasetCommentList;
import kware.common.exception.SimpleException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    /** 유형 코드는 사용 중인 DATASET_COMMENT 코드만 받는다(화면 라디오 값이지만 요청은 조작될 수 있다) */
    @Transactional
    public void saveDatasetComment(DatasetCommentSave request) {
        if (dao.countCommentType(request.getTypeCd()) == 0) {
            throw new SimpleException("지원하지 않는 의견 유형입니다.", HttpStatus.BAD_REQUEST);
        }
        CetusDatasetComment bean = new CetusDatasetComment(request);
        dao.insert(bean);
    }
}
