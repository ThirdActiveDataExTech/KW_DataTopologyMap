package kware.apps.thirdeye.comment.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.comment.domain.CetusDatasetComment;
import kware.apps.thirdeye.comment.domain.CetusDatasetCommentDao;
import kware.apps.thirdeye.comment.domain.CetusDatasetCommentType;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSave;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSearch;
import kware.apps.thirdeye.comment.dto.response.DatasetCommentList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CetusDatasetCommentService {

    private final CetusDatasetCommentDao dao;

    public Map<String, Integer> findDatasetCommentCntByType(DatasetCommentSearch search) {
        Map<String, Integer> map = new HashMap<>();
        Integer total = 0;
        for(CetusDatasetCommentType type : CetusDatasetCommentType.values()) {
            search.setTypeCd(type.name());
            Integer count = dao.getDatasetCommentCntByType(search);
            map.put(type.getCode(), count);
            total += count;
        }
        map.put("total", total);
        return map;
    }

    public Page<DatasetCommentList> findDatasetCommentPage(DatasetCommentSearch search, Pageable pageable) {
        Page<DatasetCommentList> page = dao.page("getDatasetCommentPage", "getDatasetCommentPageCount", search, pageable);
        page.getList().forEach(dto -> {
            dto.setTypeStr(CetusDatasetCommentType.getDescriptionByCode(dto.getTypeCd()));
        });
        return page;
    }

    public void saveDatasetComment(DatasetCommentSave request) {
        CetusDatasetComment bean = new CetusDatasetComment(request);
        dao.insert(bean);
    }
}
