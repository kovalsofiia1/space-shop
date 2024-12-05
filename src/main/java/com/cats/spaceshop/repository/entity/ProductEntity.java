package com.cats.spaceshop.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category"))
    CategoryEntity category;

    @Column(nullable = false, length = 100)
    String name;

    @Column(length = 255)
    String description;

    @Column(nullable = false)
    BigDecimal price;

    @Column(nullable = false)
    Integer stockQuantity;

    @Column(nullable = false, length = 50)
    String sku;
}