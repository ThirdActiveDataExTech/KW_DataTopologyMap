package kware.apps.superrole.service;

import cetus.user.UserUtil;
import kware.apps.superrole.dto.request.SetWorkplace;
import kware.common.config.auth.PrincipalDetails;
import kware.common.config.auth.PrincipalDetailsService;
import kware.common.config.auth.dto.SessionUserInfo;
import kware.common.exception.SimpleException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SuperService {

    private final HttpSession session;
    private final PrincipalDetailsService principalDetailsService;

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
}
