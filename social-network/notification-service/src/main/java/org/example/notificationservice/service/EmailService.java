package org.example.notificationservice.service;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.notificationservice.dto.Response.EmailResponse;
import org.example.notificationservice.dto.request.EmailRequest;
import org.example.notificationservice.exception.AppException;
import org.example.notificationservice.exception.ErrorCode;
import org.example.notificationservice.repository.httpclient.EmailClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

    EmailClient emailClient;

    public EmailResponse sendEmail(EmailRequest request) {
        try {
            return emailClient.senEmail(request);
        } catch (FeignException e){
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
