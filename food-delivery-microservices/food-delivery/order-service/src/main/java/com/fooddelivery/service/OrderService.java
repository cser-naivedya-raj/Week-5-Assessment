package com.fooddelivery.service;

import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.model.Order;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public ApiResponse placeOrder(Order order) {
        if (!userRepository.existsById(order.getUserId())) {
            return new ApiResponse("ERROR", "User not found with id: " + order.getUserId());
        }
        if (!restaurantRepository.existsById(order.getRestaurantId())) {
            return new ApiResponse("ERROR", "Restaurant not found with id: " + order.getRestaurantId());
        }

        order.setStatus("PENDING");
        Order saved = orderRepository.save(order);
        return new ApiResponse("SUCCESS", "Order placed successfully", saved);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public ApiResponse updateOrderStatus(Long id, String status) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(status);
            orderRepository.save(order);
            return new ApiResponse("SUCCESS", "Order status updated", order);
        }).orElse(new ApiResponse("ERROR", "Order not found with id: " + id));
    }

    public ApiResponse deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            return new ApiResponse("ERROR", "Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
        return new ApiResponse("SUCCESS", "Order deleted successfully");
    }
}
