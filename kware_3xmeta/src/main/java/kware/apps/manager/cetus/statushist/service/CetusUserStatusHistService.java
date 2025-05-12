package kware.apps.manager.cetus.statushist.service;


import cetus.user.UserUtil;
import kware.apps.manager.cetus.statushist.domain.CetusUserStatusHist;
import kware.apps.manager.cetus.statushist.domain.CetusUserStatusHistDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class CetusUserStatusHistService {

    private final CetusUserStatusHistDao dao;

    private final String reason = "사용자 관리 목록 화면에서 관리자의 사용자 상태 변경";

    @Transactional
    public void saveUserStatusHist(Long userUid, String status) {
        dao.insertUserStatusHist(new CetusUserStatusHist(userUid, status, reason));
    }
}
