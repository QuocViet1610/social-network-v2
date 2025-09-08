package org.example.notificationservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.dto.ApiResponse;
import org.example.notificationservice.dto.Response.EmailResponse;
import org.example.notificationservice.dto.request.EmailRequest;
import org.example.notificationservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    ApiResponse<EmailResponse> sendEmail(@RequestBody EmailRequest request){
        return ApiResponse.<EmailResponse>builder()
                .result(emailService.sendEmail(request))
                .build();
    }

    @KafkaListener(topics = "onboard-succesfull")
    public void listen(String message){
        log.info("message receive : {}", message );
    }

}
