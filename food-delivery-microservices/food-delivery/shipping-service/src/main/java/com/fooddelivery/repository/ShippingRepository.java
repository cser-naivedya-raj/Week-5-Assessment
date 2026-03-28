package com.fooddelivery.repository;

import com.fooddelivery.model.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    boolean existsByDeliveryId(Long deliveryId);
    Optional<Shipping> findByDeliveryId(Long deliveryId);
}
