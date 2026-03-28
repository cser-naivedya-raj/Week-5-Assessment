package com.fooddelivery.service;

import com.fooddelivery.client.PaymentClient;
import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.model.Delivery;
import com.fooddelivery.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private PaymentClient paymentClient;

    public ApiResponse createDelivery(Delivery delivery) {
        // Enforce: Payment must exist before delivery
        if (!paymentClient.paymentExistsForOrder(delivery.getOrderId())) {
            return new ApiResponse("ERROR", "Payment must be completed before creating delivery for order: " + delivery.getOrderId());
        }

        // Enforce 1:1 — one order can only have one delivery
        if (deliveryRepository.existsByOrderId(delivery.getOrderId())) {
            return new ApiResponse("ERROR", "Delivery already exists for this order");
        }

        Delivery saved = deliveryRepository.save(delivery);
        return new ApiResponse("SUCCESS", "Delivery created successfully", saved);
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Optional<Delivery> getDeliveryById(Long id) {
        return deliveryRepository.findById(id);
    }

    public Optional<Delivery> getDeliveryByOrderId(Long orderId) {
        return deliveryRepository.findByOrderId(orderId);
    }

    public ApiResponse updateDeliveryStatus(Long id, String status) {
        return deliveryRepository.findById(id).map(delivery -> {
            delivery.setStatus(status);
            deliveryRepository.save(delivery);
            return new ApiResponse("SUCCESS", "Delivery status updated", delivery);
        }).orElse(new ApiResponse("ERROR", "Delivery not found with id: " + id));
    }

    public ApiResponse deleteDelivery(Long id) {
        if (!deliveryRepository.existsById(id)) {
            return new ApiResponse("ERROR", "Delivery not found with id: " + id);
        }
        deliveryRepository.deleteById(id);
        return new ApiResponse("SUCCESS", "Delivery deleted successfully");
    }
}
