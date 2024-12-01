package com.cats.spaceshop.repository;

import com.cats.spaceshop.repository.entity.OrderEntryEntity;
import com.cats.spaceshop.repository.projection.ProductReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderEntryRepository extends JpaRepository<OrderEntryEntity, UUID> {

    @Query("SELECT oe FROM OrderEntryEntity oe WHERE oe.order.id = :orderId AND oe.product.id = :productId")
    Optional<OrderEntryEntity> findByOrderIdAndProductId(UUID orderId, UUID productId);

    List<OrderEntryEntity> findByOrderId(UUID orderId);

    @Query("SELECT p.name AS name, SUM(o.quantity) AS orderCount " +
            "FROM OrderEntryEntity o " +
            "JOIN o.product p " +
            "GROUP BY p.id " +
            "ORDER BY SUM(o.quantity) DESC")
    List<ProductReportProjection> findMostPopularProducts();
}