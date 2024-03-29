package jpcompany.smartwire.web.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberEmailService {

    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;

    // 타임리프를 사용하기 위한 객체를 의존성 주입으로 가져온다
    private final SpringTemplateEngine templateEngine;

    //실제 메일 전송
    public String sendEmail(String loginId, String toEmail, String emailAuthToken) throws MessagingException {
        //메일 전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(loginId, toEmail, emailAuthToken);

        //실제 메일 전송
        emailSender.send(emailForm);
        return emailAuthToken; // 인증 코드 반환
    }

    public String createEmailAuthToken() {
        return UUID.randomUUID().toString();
    }

    // 메일 양식 작성
    private MimeMessage createEmailForm(String loginId, String email, String emailAuthToken) throws MessagingException {

        String setFrom = "smartwire98@no-repl.com"; // 보내는 사람
        String title = "스마트와이어 회원가입 인증 메일"; // 제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); // 보낼 이메일 주소 설정
        message.setSubject(title); // 제목 설정
        message.setFrom(setFrom); // 보내는 사람 설정
        message.setText(setContext(loginId ,emailAuthToken), "utf-8", "html"); // 이메일 내용 설정
        return message;
    }

    // 타임리프를 이용한 context 설정, 보내는 내용
    private String setContext(String loginId, String emailAuthToken) {
        Context context = new Context();
        context.setVariable("emailAuthToken", emailAuthToken);
        return templateEngine.process("email/mail", context); // mail.html
    }
}
