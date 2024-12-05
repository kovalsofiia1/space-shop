package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.dto.order.OrderDto;
import com.cats.spaceshop.repository.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "cosmoCatId", source = "customer.id")
    OrderDto toDto(OrderEntity orderEntity);
}