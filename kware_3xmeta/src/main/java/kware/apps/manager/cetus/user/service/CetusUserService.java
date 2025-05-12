package kware.apps.manager.cetus.user.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.manager.cetus.dept.deptuser.service.CetusDeptUserService;
import kware.apps.manager.cetus.enumstatus.UserAuthorCd;
import kware.apps.manager.cetus.enumstatus.UserStatus;
import kware.apps.manager.cetus.statushist.service.CetusUserStatusHistService;
import kware.apps.manager.cetus.user.domain.CetusUser;
import kware.apps.manager.cetus.user.domain.CetusUserDao;
import kware.apps.manager.cetus.user.dto.request.*;
import kware.apps.manager.cetus.user.dto.response.UserExcelPage;
import kware.apps.manager.cetus.user.dto.response.UserFullInfo;
import kware.apps.manager.cetus.user.dto.response.UserList;
import kware.apps.manager.cetus.user.dto.response.UserView;
import kware.common.exception.SimpleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CetusUserService {

    private final CetusUserStatusHistService statusHistService;
    private final CetusDeptUserService deptUserService;
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
    public Page<UserList> userPage(UserListSearch search, Pageable pageable) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.userPage(search, pageable);
    }

    @Transactional
    public void userChangeInfo(UserChangeInfo request) {
        if("DEPT".equals(request.getCode())) {

            for(Long uid : request.getUsers()) {
                deptUserService.resetDeptUser(uid);
                deptUserService.saveDeptUser(request.getUid(), uid);
            }

        } else if( "STATUS".equals(request.getCode()) ) {

            for(Long uid : request.getUsers()) {
                if(!UserStatus.isValidCode(request.getValue())) {
                    throw new SimpleException("유효하지 않은 사용자 상태 코드입니다.");
                }
                dao.updateUserStatus(new CetusUser(request.getCode(), request.getValue(), uid));
                statusHistService.saveUserStatusHist(uid, request.getValue());
            }

        } else if( "AUTHOR".equals(request.getCode()) ) {

            for(Long uid : request.getUsers()) {
                if(!UserAuthorCd.isValidCode(request.getValue())) {
                    throw new SimpleException("유효하지 않은 사용자 권한 코드입니다.");
                }
                dao.updateUserAuthorCd(new CetusUser(request.getCode(), request.getValue(), uid));
            }

        }
    }
}
