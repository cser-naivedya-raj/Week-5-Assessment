package com.fooddelivery.service;

import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.model.Payment;
import com.fooddelivery.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public ApiResponse makePayment(Payment payment) {
        // Enforce 1:1 — one order can only have one payment
        if (paymentRepository.existsByOrderId(payment.getOrderId())) {
            return new ApiResponse("ERROR", "Payment already exists for this order");
        }

        Payment saved = paymentRepository.save(payment);
        return new ApiResponse("SUCCESS", "Payment processed successfully", saved);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Optional<Payment> getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public ApiResponse deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            return new ApiResponse("ERROR", "Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
        return new ApiResponse("SUCCESS", "Payment deleted successfully");
    }
}
