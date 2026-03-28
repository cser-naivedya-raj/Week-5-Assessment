package com.fooddelivery.repository;

import com.fooddelivery.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    boolean existsByOrderId(Long orderId);
    Optional<Notification> findByOrderId(Long orderId);
    List<Notification> findAllByOrderByIdDesc();
}
