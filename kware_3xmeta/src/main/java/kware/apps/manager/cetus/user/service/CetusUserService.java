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
    private final BCryptPasswordEncoder passwordEncoder;
    private final CetusUserDao dao;
    private final CommonFileService commonFileService;
    private final CetusFileService cetusFileService;
    private final CetusConfig cetusConfig;
    private final CetusDownloadsHistService downloadsHistService;

    /**
     * @method      saveUser
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription 유저 저장
    **/
    @Transactional
    public void saveUser(UserSave request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        CetusUser bean = new CetusUser(request, encodePassword);
        dao.insert(bean);
    }

    /**
     * @method      changeUser
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription 유저 수정
    **/
    @Transactional
    public void changeUser(Long uid, UserChange request) {
        CetusUser userView = dao.view(uid);
        String password = (request.getPassword() != null) ? passwordEncoder.encode(request.getPassword()) : userView.getPassword();
        dao.update(userView.changeUser(uid, request, password));
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


    /**
     * @method      findUserByUserId
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription {userId} 값을 통해 유저 전체(상세) 정보 조회
    **/
    @Transactional(readOnly = true)
    public UserFullInfo findUserByUserId(String userId) {
        return dao.getUserByUserId(userId);
    }


    @Transactional(readOnly = true)
    public UserFullInfo findUserFullInfoByUserUid(Long uid) {
        return dao.getUserFullInfoByUserUid(uid);
    }

    /**
     * @method      findUserInfoByUid
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription {uid} 값을 통해 유저 전체(상세) 정보 조회
    **/
    @Transactional(readOnly = true)
    public UserView findUserInfoByUid(Long userUid) {
        return dao.getUserInfoByUid(userUid);
    }

    /**
     * @method      view
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription {uid} 값을 통해 유저 단건 정보 조회
    **/
    @Transactional(readOnly = true)
    public CetusUser view(Long uid) {
        return dao.view(uid);
    }

    /**
     * @method      changeFailCnt
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription 비밀번호 실패 횟수 증가
    **/
    @Transactional
    public void changeFailCnt(String userId) {
        dao.updateFailCnt(new CetusUser(userId));
    }

    /**
     * @method      resetUserFailCntAndChangeLastLoginDt
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription 비밀번호 실패 횟수 초기화
    **/
    @Transactional
    public void resetUserFailCnt(String userId) {
        dao.updateUserFailCnt(new CetusUser(userId));
    }

    /**
     * @method      changeUseAt
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription 비밀번호 실패 횟수가 5회가 될 경우, {useAt} 값 변경
    **/
    @Transactional
    public void changeUseAt(String userId) {
        dao.updateUserUseAt(new CetusUser(userId));
    }

    /**
     * @method      userPage
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription 유저 페이징 목록 조회
    **/
    @Transactional(readOnly = true)
    public Page<UserList> userPage(UserListSearch search, Pageable pageable) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.userPage(search, pageable);
    }

    /**
     * @method      userChangeInfo
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription 유저 정보 수정
     *              (1) 그룹/부서 변경
     *              (2) 상태 변경
     *              (3) 권한 변경
    **/
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
}
