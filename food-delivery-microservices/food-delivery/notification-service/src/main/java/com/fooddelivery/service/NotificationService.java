package com.fooddelivery.service;

import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.model.Notification;
import com.fooddelivery.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public ApiResponse sendNotification(Notification notification) {
        // Enforce 1:1 — one order can only have one notification
        if (notificationRepository.existsByOrderId(notification.getOrderId())) {
            return new ApiResponse("ERROR", "Notification already sent for this order");
        }

        if (notification.getMessage() == null || notification.getMessage().isBlank()) {
            return new ApiResponse("ERROR", "Notification message cannot be empty");
        }

        Notification saved = notificationRepository.save(notification);
        return new ApiResponse("SUCCESS", "Notification sent successfully", saved);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllByOrderByIdDesc();
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Optional<Notification> getNotificationByOrderId(Long orderId) {
        return notificationRepository.findByOrderId(orderId);
    }

    public ApiResponse deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            return new ApiResponse("ERROR", "Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
        return new ApiResponse("SUCCESS", "Notification deleted successfully");
    }
}
