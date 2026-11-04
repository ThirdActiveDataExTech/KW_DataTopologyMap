package kware.apps.system.invite.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.databind.ObjectMapper;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.stream.Collectors;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import kware.apps.system.invite.domain.CetusInvite;
import kware.apps.system.invite.domain.CetusInviteDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 초대 메일 발송은 SMTP 디버그 설정과 무관하게 성공·실패 기록을 남기고, 실패는 호출자에게 그대로 올린다.
 */
class CetusInviteServiceTest {

    private final JavaMailSender mailSender = mock(JavaMailSender.class);
    private final CetusInviteDao dao = mock(CetusInviteDao.class);
    private final CetusInviteService service = new CetusInviteService(mailSender, dao);
    private final ListAppender<ILoggingEvent> logs = new ListAppender<>();
    private final Logger logger = (Logger) LoggerFactory.getLogger(CetusInviteService.class);
    private Level previousLevel;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "baseUrl", "http://localhost:9094");
        ReflectionTestUtils.setField(service, "sender", "KWARE <noreply@example.com>");
        when(mailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        logs.start();
        logger.addAppender(logs);
        previousLevel = logger.getLevel();
        logger.setLevel(Level.INFO);   // 테스트 로그 설정이 INFO 를 막아도 기록을 확인할 수 있게
    }

    @AfterEach
    void tearDown() {
        logger.detachAppender(logs);
        logger.setLevel(previousLevel);
    }

    private static CetusInvite request(String email) throws Exception {
        return new ObjectMapper().readValue("{\"email\":\"" + email + "\"}", CetusInvite.class);
    }

    @Test
    void 발송_성공이면_수신자를_가린_INFO_기록을_남긴다() throws Exception {
        service.sendSignupInvite(request("someone@example.com"));

        verify(dao, times(1)).insertInvite(any(CetusInvite.class));
        verify(mailSender, times(1)).send(any(MimeMessage.class));
        String info = logs.list.stream().filter(e -> e.getLevel() == Level.INFO)
                .map(ILoggingEvent::getFormattedMessage).collect(Collectors.joining("|"));
        assertThat(info).contains("초대 메일 발송 완료").contains("so***@example.com").doesNotContain("someone@");
    }

    @Test
    void 발송_실패면_WARN_기록을_남기고_예외를_그대로_올린다() throws Exception {
        doThrow(new MailSendException("smtp down")).when(mailSender).send(any(MimeMessage.class));

        assertThrows(MailSendException.class, () -> service.sendSignupInvite(request("fail@example.com")));

        String warn = logs.list.stream().filter(e -> e.getLevel() == Level.WARN)
                .map(ILoggingEvent::getFormattedMessage).collect(Collectors.joining("|"));
        assertThat(warn).contains("초대 메일 발송 실패").contains("fa***@example.com").contains("smtp down");
    }

    @Test
    void 수신자_가리기는_아이디_앞_두_글자만_남긴다() {
        assertThat(CetusInviteService.maskEmail("abcdef@kware.co.kr")).isEqualTo("ab***@kware.co.kr");
        assertThat(CetusInviteService.maskEmail("a@x.io")).isEqualTo("a***@x.io");
        assertThat(CetusInviteService.maskEmail("no-at-sign")).isEqualTo("***");
        assertThat(CetusInviteService.maskEmail(null)).isEqualTo("");
    }
}
