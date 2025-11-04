package kware.apps.superrole.workplace.service;

import cetus.user.UserUtil;
import kware.apps.superrole.workplace.dto.request.SetWorkplace;
import kware.apps.superrole.workplace.dto.request.createworkplace.CreateMenu;
import kware.apps.superrole.workplace.dto.request.createworkplace.CreateProgram;
import kware.apps.superrole.workplace.dto.request.createworkplace.CreateUser;
import kware.apps.superrole.workplace.dto.request.createworkplace.CreateWorkplace;
import kware.apps.system.menu.service.CetusMenuInfoService;
import kware.apps.system.program.service.CetusProgrmInfoService;
import kware.apps.system.user.dto.request.WorkplaceUserSave;
import kware.apps.system.user.service.CetusUserService;
import kware.apps.system.workplace.service.CetusWorkplaceService;
import kware.apps.thirdeye.enumstatus.EnumCodeDto;
import kware.apps.thirdeye.enumstatus.UserAuthorCd;
import kware.apps.thirdeye.enumstatus.UserStatus;
import kware.common.config.auth.PrincipalDetails;
import kware.common.config.auth.PrincipalDetailsService;
import kware.common.config.auth.dto.SessionUserInfo;
import kware.common.exception.SimpleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusWorkplaceSuperService {

    private final PrincipalDetailsService principalDetailsService;

    private final CetusWorkplaceService workplaceService;
    private final CetusUserService userService;
    private final CetusProgrmInfoService progrmInfoService;
    private final CetusMenuInfoService menuInfoService;

    @Transactional
    public void setSuperUserWorkplace(SetWorkplace request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!UserUtil.isAuthenticated(authentication)) throw new IllegalStateException("인증되지 않은 사용자입니다.");
        else {

            PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
            SessionUserInfo user = details.getUser();

            // SUPER 권한인지 체크
            if (!user.getIsSuper()) {
                throw new SimpleException("SUPER 권한 사용자만 워크플레이스를 선택할 수 있습니다.");
            }

            // 선택된 워크플레이스로 세션 정보 업데이트
            user.setSuperRoleInfo(request.getWorkplaceUid(), request.getWorkplaceNm());

            // 메뉴 / 브랜딩 정보 재세팅
            principalDetailsService.setUserPermissionsAndMenus(user);

            // SecurityContext 갱신
            Authentication newAuth = new UsernamePasswordAuthenticationToken( details, authentication.getCredentials(), authentication.getAuthorities() );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

    }

    @Transactional
    public void createWorkplace( CreateWorkplace request ) {

        // 1. save workplace
        Long workplaceUid = workplaceService.saveWorkplace(request.getWorkplaceNm());

        // 2. save user
        for ( CreateUser user: request.getUsers() ) {
            WorkplaceUserSave userSaveRequest = WorkplaceUserSave.builder()
                    .userId(user.getId())
                    .password(user.getPassword())
                    .userNm(user.getName())
                    .userEmail(user.getEmail())
                    .authorCd(user.getRole())
                    .status(UserStatus.APPROVED.name())
                    .build();
            userService.saveWorkplaceUser(userSaveRequest, workplaceUid);
        }

        // 3. save program
        Map<Long, Long> programUidMap = new HashMap<>(); // { temp uid, real uid }
        for ( CreateProgram program: request.getPrograms() ) {
            Long programUid = progrmInfoService.saveWorkplaceProgram(program, workplaceUid);
            programUidMap.put( program.getProgramUid(), programUid );
        }

        // 4. save menu
        this.saveMenuRecursive( request.getSystemMenus(), workplaceUid, UserAuthorCd.ROLE_SYSTEM.name(), programUidMap,  new HashMap<>());
        this.saveMenuRecursive( request.getAdminMenus(), workplaceUid, UserAuthorCd.ROLE_ADMIN.name(), programUidMap,  new HashMap<>());
        this.saveMenuRecursive( request.getUserMenus(), workplaceUid, UserAuthorCd.ROLE_USER.name(), programUidMap,  new HashMap<>());
    }

    private void saveMenuRecursive( List<CreateMenu> menus, Long workplaceUid, String authorCd,
                                    Map<Long, Long> programUidMap, Map<Long, Long> menuUidMap ) {

        for ( CreateMenu menu : menus ) {

            // programUid 매핑 처리
            Long realProgramUid = null;
            if (menu.getProgramUid() != null) {
                realProgramUid = programUidMap.getOrDefault( Long.valueOf(menu.getProgramUid()), null );
            }

            // 실제 부모 UID 가져오기
            Long actualParentUid = null;
            if ( menu.getUpperMenuNo() != null ) {
                actualParentUid = menuUidMap.get( menu.getUpperMenuNo() );
            }

            Long menuUid = menuInfoService.saveWorkplaceMenu(menu, workplaceUid, realProgramUid, actualParentUid, authorCd);

            // tempMenuUid → realMenuUid 매핑
            menuUidMap.put( menu.getMenuNo(), menuUid );

            // children 처리
            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                saveMenuRecursive(menu.getChildren(), workplaceUid, authorCd, programUidMap, menuUidMap);
            }
        }
    }

}
