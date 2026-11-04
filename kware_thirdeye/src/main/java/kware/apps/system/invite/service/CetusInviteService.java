package kware.apps.system.invite.service;

import cetus.util.StringUtil;
import kware.apps.system.invite.domain.CetusInvite;
import kware.apps.system.invite.domain.CetusInviteDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusInviteService {

    private final JavaMailSender mailSender;
    private final CetusInviteDao dao;

    @Value("${cetus.base-url}")
    private String baseUrl;

    @Value("${notification.mail.sender}")
    private String sender;

    public void sendSignupInvite(CetusInvite request) throws MessagingException {

        String token = StringUtil.random(10);
        LocalDateTime expirationDate = LocalDateTime.now().plus(5, ChronoUnit.MINUTES);
        request.setInviteInfo(token, expirationDate);
        dao.insertInvite(request);

        String signupUrl = baseUrl + "/thirdeye/invite/validate?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
        String subject = "[써드파티] 회원 가입 초대";
        StringBuilder messageCn = new StringBuilder();
        messageCn.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("<head>\r\n")
                .append("</head>\r\n")
                .append("<body>\r\n")
                .append("  <div class=\"container\" style=\"max-width: 650px; margin: 0 auto; padding: 20px;\">\r\n")
                .append("    <h1 style=\"font-size: 18px; font-weight: bold; color: #333; padding: 10px 0px;\">써드파티</h1>\r\n")
                .append("    <div class=\"content\" style=\"padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\">\r\n")
                .append("      <h2>회원 가입 초대</h2>\r\n")
                .append("      <p>써드파티에서 회원 가입 초대를 보냈습니다.</p>\r\n")
                .append("      <p>아래 버튼을 클릭하여 가입을 진행해주세요.</p>\r\n")
                .append("      <p style='margin-top: 20px; text-align: center;'>\r\n")
                .append("        <a href='").append(signupUrl).append("' ")
                .append("style=\"display: inline-block; padding: 10px 20px; background-color: #3498db; color: white; text-decoration: none; border-radius: 5px; font-family: Arial, sans-serif;\" ")
                .append("onmouseover=\"this.style.backgroundColor='#2980b9'\" ")
                .append("onmouseout=\"this.style.backgroundColor='#3498db'\" target='_blank'>회원가입</a>\r\n")
                .append("      </p>\r\n")
                .append("      <p style='margin-top: 20px;'>본 메일은 발신 전용입니다. 회신되지 않습니다.</p>\r\n")
                .append("    </div>\r\n")
                .append("  </div>\r\n")
                .append("</body>\r\n")
                .append("</html>");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(sender);
        helper.setTo(request.getEmail());
        helper.setSubject(subject);
        helper.setText(messageCn.toString(), true);

        // 발송 기록은 SMTP 디버그(mail.debug)와 무관하게 남긴다. 수신자는 일부 가린다.
        String to = maskEmail(request.getEmail());
        long started = System.currentTimeMillis();
        try {
            mailSender.send(message);
            log.info("초대 메일 발송 완료: to={}, {}ms", to, System.currentTimeMillis() - started);
        } catch (MailException e) {
            log.warn("초대 메일 발송 실패: to={}, {}ms, 원인={}", to, System.currentTimeMillis() - started, e.getMessage());
            throw e;
        }
    }

    /** 로그용 수신자 표기. 아이디 앞 두 글자만 남기고 가린다(예: ab***@example.com). */
    static String maskEmail(String email) {
        if (email == null) return "";
        int at = email.indexOf('@');
        if (at <= 0) return "***";
        String local = email.substring(0, at);
        String kept = local.substring(0, Math.min(2, local.length()));
        return kept + "***" + email.substring(at);
    }
}