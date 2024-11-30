package com.cats.spaceshop.repository.entity;

import com.cats.spaceshop.repository.entity.OrderEntry;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID customerId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderEntry> entriesList;

    // Getters and Setters
}
