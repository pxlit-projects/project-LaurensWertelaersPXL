package be.pxl.service.client;

import be.pxl.service.domain.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/api/notification")
    void createNotification(@RequestBody NotificationRequest notificationRequest);
}
