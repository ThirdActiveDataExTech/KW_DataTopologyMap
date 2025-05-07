package kware.common.config.auth.handler;

import cetus.user.UserUtil;
import kware.apps.manager.cetus.user.service.CetusUserService;
import kware.common.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final CetusUserService cetusUserService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req,
										HttpServletResponse res,
										Authentication auth
	) throws IOException {
		PrincipalDetails details = (PrincipalDetails) auth.getPrincipal();
		auth = SecurityContextHolder.getContext().getAuthentication();
		cetusUserService.resetUserFailCntAndChangeLastLoginDt(details.getUsername());
		if (UserUtil.isAuthenticated(auth)) {
			res.sendRedirect("/asp/home");
		}
	}
}
