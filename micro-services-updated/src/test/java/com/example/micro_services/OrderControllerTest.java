package com.example.micro_services;

import com.example.micro_services.model.Order;
import com.example.micro_services.model.OrderStatus;
import com.example.micro_services.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testPlaceOrder_Success() throws Exception {
    String orderJSON = """
        {
            "petId": 2,
            "quantity": 3,
            "shipDate": "2025-02-28T09:18:28.876Z",
            "status": "PLACED",
            "complete": true
        }
        """;
        mockMvc.perform(MockMvcRequestBuilders.post("/store/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("order placed"));

        Order savedOrder = orderRepository.findAll().get(0);
        assert savedOrder.getPetId() == 2;
        assert savedOrder.getQuantity() == 3;
        assert savedOrder.getStatus() == OrderStatus.PLACED;
        assert savedOrder.getShipDate().isAfter(LocalDateTime.now());
    }

    @Test
    public void testPlaceOrder_NullOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/store/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("order cannot be null"));
    }


    @Test
    public void testGetInventory_Success() throws Exception {
        orderRepository.saveAll(List.of(
                new Order(null, 1L, 5, LocalDateTime.now(),OrderStatus.APPROVED,false),
                new Order(null, 2L, 6, LocalDateTime.now(),OrderStatus.DELIVERED,false),
                new Order(null, 3L, 7, LocalDateTime.now(),OrderStatus.PLACED,false),
                new Order(null, 4L, 8, LocalDateTime.now(),OrderStatus.PLACED,false)
        ));

        mockMvc.perform(get("/store/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.PLACED").value(15))
                .andExpect(jsonPath("$.APPROVED").value(5))
                .andExpect(jsonPath("$.DELIVERED").value(6));
    }

    @Test
    public void testGetInventory_Empty() throws Exception {
        mockMvc.perform(get("/store/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetOrderById_Success() throws Exception {
        Order mockOrder = new Order(null, 1L, 5, LocalDateTime.now(),OrderStatus.PLACED,false);
        orderRepository.save(mockOrder);
        long orderId = mockOrder.getId();

        mockMvc.perform(get("/store/order/{orderId}",orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.petId").value(1L))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.status").value("PLACED"))
                .andExpect(jsonPath("$.complete").value(false));

    }

    @Test
    public void testGetOrderById_NotFound() throws Exception {
        long orderId = 999L;
        mockMvc.perform(get("/store/order/{orderId}",orderId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("invalid order id"));

    }
    @Test
    public void testDeleteOrderById_Success() throws Exception {
        Order mockOrder = new Order(null, 1L, 5, LocalDateTime.now(), OrderStatus.PLACED, false);
        orderRepository.save(mockOrder);
        mockMvc.perform(delete("/store/order/{orderId}",mockOrder.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Order deleted successfully"));
        assert orderRepository.findById(mockOrder.getId()).isEmpty();
    }

    @Test
    public void testDeleteOrderById_OrderNotFound() throws Exception {
        long orderId = 999L;
        mockMvc.perform(delete("/store/order/{orderId}",orderId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Order not found"));
    }

}
