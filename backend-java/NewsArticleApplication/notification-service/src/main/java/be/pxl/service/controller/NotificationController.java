package be.pxl.service.controller;

import be.pxl.service.domain.Notification;
import be.pxl.service.domain.dto.NotificationRequest;
import be.pxl.service.domain.dto.NotificationResponse;
import be.pxl.service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;


    //getAll van een receiverUsernameWriter
    @GetMapping("/mynotifications")
    public ResponseEntity getAllNotificationsWithReceiverUsernameWriter(@RequestHeader String receiverUsernameWriter){
        List<NotificationResponse> notificationResponses = notificationService.getAllNotificationsWithReceiverUsernameWriter(receiverUsernameWriter);
        return new ResponseEntity(notificationResponses, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNotification(@RequestBody NotificationRequest notificationRequest){
        notificationService.createNotification(notificationRequest);
    }
}
