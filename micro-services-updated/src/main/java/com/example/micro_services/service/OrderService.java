package com.example.micro_services.service;

import com.example.micro_services.model.ApiResponse;
import com.example.micro_services.model.ErrorResponse;
import com.example.micro_services.model.Order;
import com.example.micro_services.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ResponseEntity<?> placeOrder(Order order) {
        if (order == null || (order.getPetId() == null && order.getQuantity() == 0 &&
                order.getShipDate() == null && order.getStatus() == null && !order.isComplete())) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400,"order cannot be null"));
        }

        order.setShipDate(LocalDateTime.now().plusDays(5)); // Default ship date is 5 days from now
        orderRepository.save(order);
        return ResponseEntity.ok(new ApiResponse(200, "success", "order placed"));
    }

    @Cacheable(value = "order_inventory")
    public ResponseEntity<Map<String, Integer>> getInventory() {
        List<Order> orders = orderRepository.findAll();
        Map<String, Integer> inventory = new HashMap<>();
        for (Order order : orders) {
            String status = order.getStatus().toString();
            inventory.put(status, inventory.getOrDefault(status, 0) + order.getQuantity());
        }
        return ResponseEntity.ok(inventory);
    }

    @Cacheable(value = "orders", key = "#orderId")
    public ResponseEntity<?> getOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "invalid order id"));
        }
        return ResponseEntity.ok(order.get());
    }

    @CacheEvict(value = "orders", key = "#orderId")
    public ResponseEntity<?> deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "Order not found"));
        }
        orderRepository.deleteById(orderId);
        return ResponseEntity.ok(new ApiResponse(200, "success", "Order deleted successfully"));
    }


}
