package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.dto.order.OrderDto;
import com.cats.spaceshop.repository.entity.CustomerEntity;
import com.cats.spaceshop.repository.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Test
    void testToDto() {
        UUID orderId = UUID.randomUUID();
        UUID cosmoCatId = UUID.randomUUID();
        CustomerEntity customer = CustomerEntity.builder()
                .id(cosmoCatId)
                .name("Cosmo Cat")
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .id(orderId)
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .build();

        OrderDto orderDto = orderMapper.toDto(orderEntity);

        assertNotNull(orderDto);
        assertEquals(orderId, orderDto.getId());
        assertEquals(cosmoCatId, orderDto.getCosmoCatId());
    }
}
