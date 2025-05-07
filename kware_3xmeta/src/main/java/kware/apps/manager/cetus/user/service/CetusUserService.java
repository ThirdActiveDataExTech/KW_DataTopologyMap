package kware.apps.manager.cetus.user.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.manager.cetus.user.domain.CetusUser;
import kware.apps.manager.cetus.user.domain.CetusUserDao;
import kware.apps.manager.cetus.user.dto.request.UserChange;
import kware.apps.manager.cetus.user.dto.request.UserExcelSearch;
import kware.apps.manager.cetus.user.dto.request.UserSave;
import kware.apps.manager.cetus.user.dto.response.UserExcelPage;
import kware.apps.manager.cetus.user.dto.response.UserFullInfo;
import kware.apps.manager.cetus.user.dto.response.UserView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CetusUserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final CetusUserDao dao;

    @Transactional
    public void saveUser(UserSave request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        CetusUser bean = new CetusUser(request, encodePassword);
        dao.insert(bean);
    }

    @Transactional
    public void changeUser(Long uid, UserChange request) {
        CetusUser userView = dao.view(uid);
        String password = (request.getPassword() != null) ? passwordEncoder.encode(request.getPassword()) : userView.getPassword();
        dao.update(userView.changeUser(uid, request, password));
    }

    @Transactional(readOnly = true)
    public Page<UserExcelPage> excelPage(UserExcelSearch bean, Pageable pageable) {
        return dao.excelPage(bean, pageable);
    }

    @Transactional(readOnly = true)
    public UserFullInfo findUserByUserId(String userId) {
        return dao.getUserByUserId(userId);
    }

    @Transactional(readOnly = true)
    public UserView findUserInfoByUid(Long userUid) {
        return dao.getUserInfoByUid(userUid);
    }

    @Transactional(readOnly = true)
    public CetusUser view(Long uid) {
        return dao.view(uid);
    }

    @Transactional
    public void changeFailCnt(String userId) {
        dao.updateFailCnt(new CetusUser(userId));
    }

    @Transactional
    public void resetUserFailCntAndChangeLastLoginDt(String userId) {
        dao.updateUserFailCntAndChangeLastLoginDt(new CetusUser(userId));
    }

    @Transactional
    public void changeUseAt(String userId) {
        dao.updateUserUseAt(new CetusUser(userId));
    }

    @Transactional(readOnly = true)
    public Integer findUserCntByUserEmail(String userEmail) {
        return dao.getUserCntByUserEmail(userEmail);
    }
}
