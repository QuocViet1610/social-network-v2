package org.example.notificationservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.dto.request.EmailRequest;
import org.example.notificationservice.event.dto.NotificationEvent;
import org.example.notificationservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    EmailService emailService;
    @KafkaListener(topics = "topic")
    public void listenNotificationDelivery(String message){
        log.info("Message received: {}", message);
//        emailService.sendEmail(EmailRequest.builder()
//                .email(message.getSubject())
//                .emailContent(message.getBody())
//                .build());
    }
}
