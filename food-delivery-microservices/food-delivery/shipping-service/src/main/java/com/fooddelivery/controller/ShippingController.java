package com.fooddelivery.controller;

import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.model.Shipping;
import com.fooddelivery.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping
    public ResponseEntity<ApiResponse> createShipping(@RequestBody Shipping shipping) {
        ApiResponse response = shippingService.createShipping(shipping);
        if ("ERROR".equals(response.getStatus())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Shipping>> getAllShippings() {
        return ResponseEntity.ok(shippingService.getAllShippings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShippingById(@PathVariable Long id) {
        return shippingService.getShippingById(id)
                .map(s -> ResponseEntity.ok(new ApiResponse("SUCCESS", "Shipping found", s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<?> getShippingByDelivery(@PathVariable Long deliveryId) {
        return shippingService.getShippingByDeliveryId(deliveryId)
                .map(s -> ResponseEntity.ok(new ApiResponse("SUCCESS", "Shipping found", s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id,
                                                     @RequestParam String status) {
        ApiResponse response = shippingService.updateShippingStatus(id, status);
        if ("ERROR".equals(response.getStatus())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteShipping(@PathVariable Long id) {
        ApiResponse response = shippingService.deleteShipping(id);
        if ("ERROR".equals(response.getStatus())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
