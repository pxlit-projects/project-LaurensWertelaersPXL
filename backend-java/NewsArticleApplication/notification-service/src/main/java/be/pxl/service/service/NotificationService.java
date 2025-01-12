package be.pxl.service.service;

import be.pxl.service.domain.Notification;
import be.pxl.service.domain.dto.NotificationRequest;
import be.pxl.service.domain.dto.NotificationResponse;
import be.pxl.service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void createNotification(NotificationRequest notificationRequest){
        Notification notification = Notification.builder()
                .newsArticleId(notificationRequest.getNewsArticleId())
                .receiverUsernameWriter(notificationRequest.getReceiverUsernameWriter())
                .senderUsernameEditor(notificationRequest.getSenderUsernameEditor())
                .approvedOrDisapproved(notificationRequest.getApprovedOrDisapproved())
                .remark(notificationRequest.getRemark())
                .creationDate(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

    }

    private NotificationResponse mapToNotificationResponse(Notification notification){
        return NotificationResponse.builder()
                .id(notification.getId())
                .newsArticleId(notification.getNewsArticleId())
                .receiverUsernameWriter(notification.getReceiverUsernameWriter())
                .senderUsernameEditor(notification.getSenderUsernameEditor())
                .approvedOrDisapproved(notification.getApprovedOrDisapproved())
                .remark(notification.getRemark())
                .creationDate(notification.getCreationDate())
                .build();
    }

    public List<NotificationResponse> getAllNotificationsWithReceiverUsernameWriter(String receiverUsernameWriter) {
        List<Notification> notifications = notificationRepository.getNotificationsByReceiverUsernameWriter(receiverUsernameWriter);

        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification notification : notifications){
            notificationResponses.add(mapToNotificationResponse(notification));
        }

        return notificationResponses;
    }
}
