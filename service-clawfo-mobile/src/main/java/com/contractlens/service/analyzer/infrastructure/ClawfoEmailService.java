package com.contractlens.service.analyzer.infrastructure;

import com.contractlens.common.util.LoaderTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ClawfoEmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;


    public void sendRegistrationOtp(
            String email,
            String otp
    ) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                false,
                StandardCharsets.UTF_8.name()
        );

        helper.setFrom(senderEmail, "CLAwfo");
        helper.setTo(email);
        helper.setSubject("CLAwfo Registration OTP");

        String html = null;
        try {
            html = LoaderTemplate.loadTemplate(
                    "template/email/clawfo-registration-otp.html"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        html = html.replace("{{OTP}}", otp);
        helper.setText(html, true);

        mailSender.send(message);
    }

}
