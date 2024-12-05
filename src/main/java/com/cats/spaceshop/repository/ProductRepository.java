package com.cats.spaceshop.repository;

import com.cats.spaceshop.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    @Query("SELECT p FROM ProductEntity p WHERE p.category.id = :categoryId")
    List<ProductEntity> findByCategoryId(@Param("categoryId") UUID categoryId);
}
