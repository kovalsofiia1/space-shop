package com.cats.spaceshop.repository.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_entry")
public class OrderEntry {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    // Getters and Setters
}
