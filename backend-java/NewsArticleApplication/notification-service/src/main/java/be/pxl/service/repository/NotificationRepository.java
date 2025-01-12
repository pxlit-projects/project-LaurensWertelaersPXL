package be.pxl.service.repository;

import be.pxl.service.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> getNotificationsByReceiverUsernameWriter(String receiverUsernameWriter);
}
