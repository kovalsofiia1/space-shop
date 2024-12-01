package com.cats.spaceshop.repository;

import com.cats.spaceshop.repository.entity.OrderEntity;
import com.cats.spaceshop.repository.projection.ProductReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}