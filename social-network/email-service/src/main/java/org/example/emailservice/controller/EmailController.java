package org.example.emailservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.emailservice.dto.ApiResponse;
import org.example.emailservice.dto.Response.EmailResponse;
import org.example.emailservice.dto.request.EmailRequest;
import org.example.emailservice.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {

    EmailService emailService;

    @PostMapping("/send")
    EmailResponse create(@RequestBody EmailRequest request) {
        return  emailService.sendVerificationEmail(request);
    }


}
