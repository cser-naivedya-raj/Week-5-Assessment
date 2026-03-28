package com.fooddelivery.service;

import com.fooddelivery.client.DeliveryClient;
import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.model.Shipping;
import com.fooddelivery.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private DeliveryClient deliveryClient;

    public ApiResponse createShipping(Shipping shipping) {
        // Enforce: Delivery must exist before shipping
        if (!deliveryClient.deliveryExists(shipping.getDeliveryId())) {
            return new ApiResponse("ERROR", "Delivery not found with id: " + shipping.getDeliveryId());
        }

        // Enforce 1:1 — one delivery can only have one shipping record
        if (shippingRepository.existsByDeliveryId(shipping.getDeliveryId())) {
            return new ApiResponse("ERROR", "Shipping already exists for this delivery");
        }

        if (shipping.getStatus() == null) {
            shipping.setStatus("PENDING");
        }

        Shipping saved = shippingRepository.save(shipping);
        return new ApiResponse("SUCCESS", "Shipping details created successfully", saved);
    }

    public List<Shipping> getAllShippings() {
        return shippingRepository.findAll();
    }

    public Optional<Shipping> getShippingById(Long id) {
        return shippingRepository.findById(id);
    }

    public Optional<Shipping> getShippingByDeliveryId(Long deliveryId) {
        return shippingRepository.findByDeliveryId(deliveryId);
    }

    public ApiResponse updateShippingStatus(Long id, String status) {
        return shippingRepository.findById(id).map(shipping -> {
            shipping.setStatus(status);
            shippingRepository.save(shipping);
            return new ApiResponse("SUCCESS", "Shipping status updated", shipping);
        }).orElse(new ApiResponse("ERROR", "Shipping not found with id: " + id));
    }

    public ApiResponse deleteShipping(Long id) {
        if (!shippingRepository.existsById(id)) {
            return new ApiResponse("ERROR", "Shipping not found with id: " + id);
        }
        shippingRepository.deleteById(id);
        return new ApiResponse("SUCCESS", "Shipping deleted successfully");
    }
}
