package com.yuyun.choiceapp.member.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${yuyun.serverUrl}")
    private String domainUrl;

    private final JavaMailSender mailSender;

    public MimeMessage createMessage(String email, long memberId, String authCode) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        String certificationUrl = createUrl(memberId, authCode);

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("ChoiceAPP 회원가입 이메일 인증");

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> ChoiceAPP 입니다</h1>";
        msgg += "LINK : <a href='";
        msgg += certificationUrl + "'>인증 링크</a><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");

        return message;
    }

    private String createUrl(long memberId, String authCode) {
        return domainUrl + "/api/members/" + memberId + "/verify?authCode=" + authCode;
    }

    // 메일 발송
    public void send(String email, long memberId, String authCode) throws MessagingException, UnsupportedEncodingException {
        MimeMessage emailForm = createMessage(email, memberId, authCode);
        mailSender.send(emailForm);
    }

}
