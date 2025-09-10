package kware.common.config.auth.handler;

import kware.common.config.auth.PrincipalDetails;
import kware.common.config.auth.dto.SessionUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof PrincipalDetails) {
            PrincipalDetails principal = (PrincipalDetails) auth.getPrincipal();
            SessionUserInfo user = principal.getUser();
            log.info("Forbidden access attempt. userId: {} (Role: {}) tried to access URI {}", user.getUserId(), user.getRole(), req.getRequestURI());
        }

        String ajaxHeader = req.getHeader("X-Requested-With");
        String requestURI = req.getRequestURI();

        // /login 요청이면 별도 처리
        if ("/login".equals(requestURI)) {
            // 그냥 이전 페이지로 리디렉션 (alert 없이)
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("text/html;charset=UTF-8");

            PrintWriter out = res.getWriter();
            out.println("<script>");
            out.println("window.location.href = window.history.back();");  // 이전 페이지가 없으면 루트로
            out.println("</script>");
            out.flush();
            return;
        }

        // AJAX 요청인지 검사 (헤더 검사, 비동기인지 체크)
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        if (isAjax) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "해당 페이지에 대한 접근 권한이 없습니다.");
        } else {
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("text/html;charset=UTF-8");

            PrintWriter out = res.getWriter();
            out.println("<script>");
            out.println("alert('해당 페이지에 대한 접근 권한이 없습니다.');");
            out.println("window.location.href = window.history.back();");
            out.println("</script>");
            out.flush();
        }
    }
}
