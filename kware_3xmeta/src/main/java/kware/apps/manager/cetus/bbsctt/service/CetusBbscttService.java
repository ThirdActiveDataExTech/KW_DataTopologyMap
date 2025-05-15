package kware.apps.manager.cetus.bbsctt.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import cetus.util.CookieUtil;
import kware.apps.manager.cetus.bbsctt.domain.CetusBbsctt;
import kware.apps.manager.cetus.bbsctt.domain.CetusBbscttDao;
import kware.apps.manager.cetus.bbsctt.dto.request.BbscttChange;
import kware.apps.manager.cetus.bbsctt.dto.request.BbscttSave;
import kware.apps.manager.cetus.bbsctt.dto.request.BbscttSearch;
import kware.apps.manager.cetus.bbsctt.dto.response.BbscttList;
import kware.apps.manager.cetus.bbsctt.dto.response.BbscttView;
import kware.common.file.service.CommonFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class CetusBbscttService {

    private final CetusBbscttDao dao;
    private final CommonFileService commonFileService;

    @Transactional(readOnly = true)
    public Page<BbscttList> findAllBbscttPage(BbscttSearch search, Pageable pageable) {
        return dao.page("bbscttList", "bbscttCount", search, pageable);
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
}
