package com.project.ModuleReclamation.feign;

import com.project.ModuleShared.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.management.Notification;

@FeignClient(name = "ModuleNotification")
public interface NotificationFeignClient {

    @PostMapping("/api/notifications/send")
    ResponseEntity<Notification> createAndSendNotification(@RequestBody NotificationRequest request);
}
