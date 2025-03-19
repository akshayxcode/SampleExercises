package com.example.micro_services.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long petId;
    private int quantity;
    private LocalDateTime shipDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private boolean complete;

    public void setShipDate(LocalDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }


    public Long getPetId() {
        return petId;
    }

    public LocalDateTime getShipDate() {
        return shipDate;
    }

    public Long getId() {
        return id;
    }

    public boolean isComplete() {
        return complete;
    }

}
