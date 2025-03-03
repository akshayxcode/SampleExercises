package com.example.micro_services.controller;

import com.example.micro_services.model.ApiResponse;
import com.example.micro_services.model.ErrorResponse;
import com.example.micro_services.model.Order;
import com.example.micro_services.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> placeOrder(@RequestBody Order order) {
        if (order == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "error", "order cannot be null"));
        }
        order.setShipDate(LocalDateTime.now().plusDays(5)); // Default ship date is 5 days from now
        orderRepository.save(order);
        return ResponseEntity.ok(new ApiResponse(200,"success","order placed"));
    }

    @GetMapping("/inventory")
    public ResponseEntity<Map<String, Integer>> getInventory() {
        List<Order> orders = orderRepository.findAll();
        Map<String,Integer> inventory = new HashMap<>();
        for (Order order: orders ) {
            String status = order.getStatus().toString();
            inventory.put(status,inventory.getOrDefault(status, 0) + order.getQuantity());
        }
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()){
            return ResponseEntity.status(404).body(new ErrorResponse(404,"invalid user id"));
        }
        return ResponseEntity.ok(order.get());
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            return ResponseEntity.badRequest().body(new ApiResponse(404, "error", "Order not found"));
        }
        orderRepository.deleteById(orderId);
        return ResponseEntity.ok(new ApiResponse(200, "success", "Order deleted successfully"));
    }



}
