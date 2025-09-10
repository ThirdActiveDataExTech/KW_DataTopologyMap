package kware.apps.thirdeye.answer.domain;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.dao.SuperDao;
import kware.apps.thirdeye.answer.dto.request.AnswerExcelSearch;
import kware.apps.thirdeye.answer.dto.response.AnswerExcelList;
import kware.apps.thirdeye.answer.dto.response.AnswerList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusBbscttAnswerDao extends SuperDao<CetusBbscttAnswer> {

    public CetusBbscttAnswerDao() {
        super("cetusBbscttAnswer");
    }

    public List<AnswerList> getAllAnswerList(Long bbscttUid) {
        return selectList("getAllAnswerList", bbscttUid);
    }

    public Page<AnswerExcelList> answerExcelPage(AnswerExcelSearch search, Pageable pageable) {
        return page("answerExcelPageList", "answerExcelPageListCount", search, pageable);
    }
}
