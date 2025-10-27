package kware.apps.thirdeye.bbsctt.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import cetus.util.CookieUtil;
import kware.apps.system.bbs.domain.CetusBbs;
import kware.apps.system.bbs.service.CetusBbsService;
import kware.apps.thirdeye.bbsctt.domain.CetusBbsctt;
import kware.apps.thirdeye.bbsctt.domain.CetusBbscttDao;
import kware.apps.thirdeye.bbsctt.dto.request.*;
import kware.apps.thirdeye.bbsctt.dto.response.BbscttExcelList;
import kware.apps.thirdeye.bbsctt.dto.response.BbscttList;
import kware.apps.thirdeye.bbsctt.dto.response.BbscttView;
import kware.apps.thirdeye.enumstatus.BbsTpCd;
import kware.apps.thirdeye.enumstatus.DownloadTargetCd;
import kware.common.excel.ExcelCreate;
import kware.common.file.service.CommonFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CetusBbscttService {

    private final CetusBbscttDao dao;
    private final CommonFileService commonFileService;
    private final ExcelCreate excelCreate;
    private final CetusBbsService bbsService;

    @Value("${cetus.base-url}")
    private String baseUrl;

    @Transactional(readOnly = true)
    public Page<BbscttList> findAllBbscttPage(BbscttSearch search, Pageable pageable) {
        Page<BbscttList> page = dao.page("bbscttPageList", "bbscttPageListCount", search, pageable);
        page.getList().forEach(dto -> {
            dto.setBbs(BbsTpCd.getSubCodeByCode(dto.getBbsTpCd()));
        });
        return page;
    }

    @Transactional
    public void saveBbsctt(BbscttSave request) {
        Long fileUid = commonFileService.processFileBean(request, UserUtil.getUser(), null);
        dao.insert(new CetusBbsctt(request, fileUid));
    }

    @Transactional(readOnly = true)
    public CetusBbsctt view(Long bbscttUid) {
        return dao.view(bbscttUid);
    }

    @Transactional
    public void changeBbsctt(Long bbscttUid, BbscttChange request) {
        Long fileUid = request.getFileUid();
        fileUid = commonFileService.processFileBean(request, UserUtil.getUser(), fileUid);
        CetusBbsctt view = dao.view(bbscttUid);
        dao.update(view.changeBbsctt(bbscttUid, request, fileUid));
    }

    @Transactional
    public void deleteBbsctt(Long uid) {
        dao.delete(uid);
    }

    @Transactional
    public void increaseViewCount(Long bbscttUid, HttpServletRequest req, HttpServletResponse res) {
        String userId = UserUtil.getUser().getUserId();
        String cookie = CookieUtil.getCookie(req, "bbsctt" + userId);
        if (cookie == null) {
            CookieUtil.createCookie(res, "bbsctt" + userId, "[" + userId + "]", 60);
            dao.increaseViewCount(bbscttUid);
        }
    }

    @Transactional(readOnly = true)
    public BbscttView findViewByBbscttUid(Long bbscttUid) {
        return dao.getViewByBbscttUid(bbscttUid);
    }

    @Transactional
    public void changeBbscttOpenAt(BbscttChangeOpenAt request) {
        for(Long uid : request.getUids()) {
            dao.updateBbscttOpenAt(new CetusBbsctt(uid, request.getOpenAt()));
        }
    }

    @Transactional
    public void deleteBbsctts(BbscttDelete request) {
        for(Long uid : request.getUids()) {
            dao.delete(uid);
        }
    }

    @Async
    public void renderEXCEL(BbscttExcelSearch search) {
        CetusBbs bbs = bbsService.view(search.getBbsUid());
        String subCode = BbsTpCd.getSubCodeByCode(bbs.getBbsTpCd());
        search.setBaseUrl(baseUrl, subCode);
        excelCreate.createExcelFile(
                BbscttExcelList.class,
                p -> {
                    Page<BbscttExcelList> excelPage = dao.bbscttExcelPage(search, p);
                    List<BbscttExcelList> list = excelPage.getList().stream().map(dto -> {
                        dto.setBbsTpCdNm(BbsTpCd.getSubNameByCode(dto.getBbsTpCd()));
                        return dto;
                    }).collect(Collectors.toList());
                    excelPage.setList(list);
                    return excelPage;
                },
                DownloadTargetCd.USER_BBSCTT
        );
    }


    public Integer changeBbscttLikeCount(Long uid) {
        dao.updateBbscttLikeCount(uid);
        return dao.getBbscttLikeCount(uid);
    }
}
