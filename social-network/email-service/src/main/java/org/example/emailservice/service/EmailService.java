package org.example.emailservice.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.emailservice.dto.Response.EmailResponse;
import org.example.emailservice.dto.request.EmailRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

    JavaMailSender mailSender;

    public EmailResponse sendVerificationEmail(EmailRequest email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email.getEmail());
            helper.setSubject("Xác nhận tài khoản");

            helper.setText(email.getEmailContent(), true);
            helper.setFrom("vietddls06@gmail.com", "Test ");
            mailSender.send(message);
            EmailResponse emailResponse = EmailResponse.builder().emailTo(email.getEmail())
                    .emailContent(email.getEmailContent())
                    .build();
            return emailResponse;
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }


}
