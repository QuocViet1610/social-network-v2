package org.example.notificationservice.repository.httpclient;

import org.example.notificationservice.dto.ApiResponse;
import org.example.notificationservice.dto.Response.EmailResponse;
import org.example.notificationservice.dto.request.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-client", url = "${notification.email.url}")
public interface EmailClient {

    @PostMapping("/send")
    EmailResponse senEmail(@RequestBody EmailRequest request);

}
