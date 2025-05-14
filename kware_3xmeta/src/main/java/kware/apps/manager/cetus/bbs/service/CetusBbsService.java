package kware.apps.manager.cetus.bbs.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.manager.cetus.bbs.domain.CetusBbs;
import kware.apps.manager.cetus.bbs.domain.CetusBbsDao;
import kware.apps.manager.cetus.bbs.dto.request.BbsChange;
import kware.apps.manager.cetus.bbs.dto.request.BbsSave;
import kware.apps.manager.cetus.bbs.dto.request.BbsSearch;
import kware.apps.manager.cetus.bbs.dto.response.BbsList;
import kware.apps.manager.cetus.enumstatus.BbsTpCd;
import kware.common.exception.SimpleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusBbsService {

    private final CetusBbsDao dao;

    @Transactional
    public void saveBbs(BbsSave request) {
        if(!BbsTpCd.isValidCode(request.getBbsTpCd())) {
            throw new SimpleException("유효하지 않은 게시판 유형 코드입니다.");
        }
        dao.insert(new CetusBbs(request));
    }

    @Transactional(readOnly = true)
    public Page<BbsList> findAllBbsPage(BbsSearch search, Pageable pageable) {
        return dao.getAllBbsPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public CetusBbs view(Long uid) {
        return dao.view(uid);
    }

    @Transactional
    public void changeBbs(Long uid, BbsChange request) {
        if(!BbsTpCd.isValidCode(request.getBbsTpCd())) {
            throw new SimpleException("유효하지 않은 게시판 유형 코드입니다.");
        }
        CetusBbs view = dao.view(uid);
        dao.update(view.changeBbs(uid, request));
    }

    @Transactional
    public void deleteBbs(Long uid) {
        dao.delete(uid);
    }

    @Transactional(readOnly = true)
    public List<BbsList> findAllWorkplaceBbs() {
        return dao.getAllWorkplaceBbs(UserUtil.getUserWorkplaceUid());
    }
}
