package kware.apps.thirdeye.answer.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.system.bbs.dto.response.BbsView;
import kware.apps.system.bbs.service.CetusBbsService;
import kware.apps.thirdeye.answer.domain.CetusBbscttAnswer;
import kware.apps.thirdeye.answer.domain.CetusBbscttAnswerDao;
import kware.apps.thirdeye.answer.dto.request.*;
import kware.apps.thirdeye.answer.dto.response.AnswerExcelList;
import kware.apps.thirdeye.answer.dto.response.AnswerList;
import kware.apps.thirdeye.enumstatus.DownloadTargetCd;
import kware.common.excel.ExcelCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusBbscttAnswerService {

    private final CetusBbscttAnswerDao dao;
    private final CetusBbsService bbsService;
    private final ExcelCreate excelCreate;

    @Value("${cetus.base-url}")
    private String baseUrl;

    @Transactional
    public void saveAnswer(AnswerSave request) {
        dao.insert(new CetusBbscttAnswer(request));
    }

    @Transactional(readOnly = true)
    public List<AnswerList> findAllAnswerList(Long bbscttUid) {
        return dao.getAllAnswerList(bbscttUid);
    }

    @Transactional(readOnly = true)
    public Page<AnswerList> findAllAnswerPage(Long bbscttUid, Pageable pageable) {
        return dao.page("getAllAnswerPage", "getAllAnswerPageCount", new AnswerPageSearch(bbscttUid), pageable);
    }

    @Transactional
    public void deleteAnswer(Long uid) {
        dao.delete(uid);
    }

    @Transactional
    public Page<AnswerList> findAllAnswerPage(AnswerSearch search, Pageable pageable) {
        return dao.page("answerBbsRegPageList", "answerBbsRegPageListCount", search, pageable);
    }

    @Transactional
    public void deleteAnswers(AnswerDelete request) {
        for(Long uid : request.getUids()) {
            dao.delete(uid);
        }
    }

    @Async
    public void renderEXCEL(AnswerExcelSearch search) {
        BbsView bbsView = bbsService.findBbsByUid(search.getBbsUid());
        search.setBaseUrl(baseUrl, bbsView.getBbsTpSubCd());
        excelCreate.createExcelFileHtmlCustom(
                AnswerExcelList.class,
                p -> dao.answerExcelPage(search, p),
                DownloadTargetCd.USER_ANSWER
        );
    }

    @Transactional
    public void changeAnswer(Long uid, AnswerChange request) {
        dao.update(new CetusBbscttAnswer(uid, request));
    }
}
