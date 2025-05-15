package kware.common.config.auth.handler;

import cetus.user.UserUtil;
import kware.apps.manager.cetus.loginhist.dto.request.UserLoginHistSave;
import kware.apps.manager.cetus.loginhist.service.CetusUserLoginHistService;
import kware.apps.manager.cetus.user.service.CetusUserService;
import kware.common.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final CetusUserService cetusUserService;
	private final CetusUserLoginHistService loginHistService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req,
										HttpServletResponse res,
										Authentication auth
	) throws IOException {
		PrincipalDetails details = (PrincipalDetails) auth.getPrincipal();
		auth = SecurityContextHolder.getContext().getAuthentication();
		cetusUserService.resetUserFailCnt(details.getUsername());
		if (UserUtil.isAuthenticated(auth)) {
			Long uid = details.getUser().getUid();
			this.saveLoginHist(uid, req);
			res.sendRedirect("/asp/home");
		}
	}

	private void saveLoginHist(Long uid, HttpServletRequest req) {
		// 접속 IP
		String ip = req.getHeader("X-Forwarded-For");
		if( ip == null ) {
			ip = req.getRemoteAddr();
		}

		// 접근 URL
		String referer = req.getHeader("Referer");

		// 접속 브라우저
		String userAgent = req.getHeader("User-Agent");
		String browser = this.searchBrowser(userAgent);

		// Session ID
		HttpSession session = req.getSession(false);
		String sessionId = (session != null) ? session.getId() : null;

		UserLoginHistSave build = UserLoginHistSave.insertLoginHist()
				.userUid(uid)
				.loginIp(ip)
				.loginBrowser(browser)
				.loginAccessUrl(referer)
				.sessionId(sessionId)
				.build();
		loginHistService.saveUserLoginHist(build);
	}

	private String searchBrowser(String userAgent) {
		String browser 	 = "";
		if(userAgent.indexOf("Trident") > -1) {												// IE
			browser = "ie";
		} else if(userAgent.indexOf("Edge") > -1) {											// Edge
			browser = "edge";
		} else if(userAgent.indexOf("Whale") > -1) { 										// Naver Whale
			browser = "whale";
		} else if(userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) { 		// Opera
			browser = "opera";
		} else if(userAgent.indexOf("Firefox") > -1) { 										 // Firefox
			browser = "firefox";
		} else if(userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1 ) {	 // Safari
			browser = "safari";
		} else if(userAgent.indexOf("Chrome") > -1) {										 // Chrome
			browser = "chrome";
		}
		return browser;
	}
}
