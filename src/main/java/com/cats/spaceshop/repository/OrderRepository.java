package com.cats.spaceshop.repository;

import com.cats.spaceshop.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}