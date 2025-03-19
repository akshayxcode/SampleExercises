package com.example.micro_services.controller;
import com.example.micro_services.model.Order;
import com.example.micro_services.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("api/v1/store")
public class OrderControllerV1 {

    private final OrderService orderService;

    @Autowired
    public OrderControllerV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @GetMapping("/inventory")
    public ResponseEntity<Map<String, Integer>> getInventory() {
        return orderService.getInventory();
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        return orderService.deleteOrder(orderId);
    }
}
