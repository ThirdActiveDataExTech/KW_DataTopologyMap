package kware.apps.manager.cetus.user.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.config.CetusConfig;
import cetus.user.UserUtil;
import kware.apps.manager.cetus.dept.deptuser.service.CetusDeptUserService;
import kware.apps.manager.cetus.downloadshist.service.CetusDownloadsHistService;
import kware.apps.manager.cetus.enumstatus.DownloadTargetCd;
import kware.apps.manager.cetus.enumstatus.UserAuthorCd;
import kware.apps.manager.cetus.enumstatus.UserStatus;
import kware.apps.manager.cetus.file.service.CetusFileService;
import kware.apps.manager.cetus.group.groupuser.service.CetusGroupUserService;
import kware.apps.manager.cetus.position.positionuser.domain.CetusPositionUser;
import kware.apps.manager.cetus.position.positionuser.service.CetusPositionUserService;
import kware.apps.manager.cetus.statushist.service.CetusUserStatusHistService;
import kware.apps.manager.cetus.user.domain.CetusUser;
import kware.apps.manager.cetus.user.domain.CetusUserDao;
import kware.apps.manager.cetus.user.dto.request.*;
import kware.apps.manager.cetus.user.dto.response.UserExcelPage;
import kware.apps.manager.cetus.user.dto.response.UserFullInfo;
import kware.apps.manager.cetus.user.dto.response.UserList;
import kware.apps.manager.cetus.user.dto.response.UserView;
import kware.common.excel.ExcelRender;
import kware.common.exception.SimpleException;
import kware.common.file.service.CommonFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CetusUserService {

    private final CetusUserStatusHistService statusHistService;
    private final CetusDeptUserService deptUserService;
    private final CetusGroupUserService groupUserService;
    private final CetusPositionUserService positionUserService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CetusUserDao dao;
    private final CommonFileService commonFileService;
    private final CetusFileService cetusFileService;
    private final CetusConfig cetusConfig;
    private final CetusDownloadsHistService downloadsHistService;

    @Transactional
    public void saveUser(UserSave request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        CetusUser bean = new CetusUser(request, encodePassword);
        dao.insert(bean);
    }

    @Transactional
    public void changeUser(Long uid, UserChange request) {
        if(!UserAuthorCd.isValidCode(request.getUserAuthor())) {
            throw new SimpleException("유효하지 않은 사용자 권한 코드입니다.");
        }
        // 1. user
        CetusUser userView = dao.view(uid);
        dao.updateUserInfo(userView.changeUser(uid, request));

        // 2. dept
        deptUserService.resetDeptUser(uid);
        deptUserService.saveDeptUser(request.getUserDept(), uid);

        // 3. group
        groupUserService.resetGroupUser(uid);
        groupUserService.saveGroupUser(request.getUserGroup(), uid);

        // 4. position
        positionUserService.resetPositionUser(uid);
        positionUserService.savePositionUser(request.getUserPosition(), uid);

        // 5. status
        if( request.getUserStatus() != null ) {
            dao.updateUserStatus(new CetusUser("STATUS", request.getUserStatus(), uid));
            statusHistService.saveUserStatusHistWithReason(uid, request.getUserStatus(), request.getChangeReason());
        }
    }

    @Transactional
    public void changeUserPassword(Long uid, UserPasswordChange request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        CetusUser userView = dao.view(uid);
        dao.updateUserPassword(userView.changeUserPassword(uid, encodePassword));
    }

    @Async
    public void renderEXCEL(UserExcelSearch search) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);
        String fileName = "유저목록_다운로드_" + timestamp + ".xlsx";

        Long fileUid = commonFileService.generateUid();
        cetusFileService.saveFile(fileUid, fileName);
        downloadsHistService.saveDownloadsHistUser(DownloadTargetCd.USER.name(), fileUid);

        try {

            Pageable pageable = new Pageable(1000);
            search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
            Page<UserExcelPage> paging = dao.userExcelPage(search, pageable);
            ExcelRender excel = new ExcelRender(UserExcelPage.class);
            for (int i = 0; i < paging.getTotalPages(); i++) {
                pageable.setPageNumber(i + 1);
                excel.renderExcel(dao.userExcelPage(search, pageable).getList());
            }
            File file = excel.writeWorkBookToFile(cetusConfig.getDownloadPath(), fileName);
            cetusFileService.updateDownloadFile(fileUid, file);

        } catch (IOException e) {
            cetusFileService.deleteNotDownloadFile(fileUid);
        }

    }

    @Transactional(readOnly = true)
    public UserFullInfo findUserByUserId(String userId) {
        return dao.getUserByUserId(userId);
    }


    @Transactional(readOnly = true)
    public UserFullInfo findUserFullInfoByUserUid(Long uid) {
        return dao.getUserFullInfoByUserUid(uid);
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
    public void resetUserFailCnt(String userId) {
        dao.updateUserFailCnt(new CetusUser(userId));
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

    @Transactional
    public void changeUserProfile(Long uid, UserProfile request) {
        Long profileUid = (request.getProfileUid() != null) ? request.getProfileUid() : null;
        profileUid = commonFileService.processFileBean(request, UserUtil.getUser(), profileUid);
        dao.updateUserProfile(new CetusUser(uid, profileUid));
    }

    /**
     * 이미 등록된 userEmail 있는지 체크
     * */
    public Integer findByEmail(String email) {
        return dao.findByEmail(email);
    }

    /**
     * 이미 등록된 userId 있는지 체크
     * */
    public Integer findByUserId(CetusUser cetusUser) {
        return dao.findByUserId(cetusUser);
    }
}
