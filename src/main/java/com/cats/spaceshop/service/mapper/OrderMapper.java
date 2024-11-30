package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.order.Order;
import com.cats.spaceshop.dto.order.OrderCreateDto;
import com.cats.spaceshop.dto.order.OrderDto;
import com.cats.spaceshop.repository.entity.OrderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderEntity toEntity(OrderDto orderDto);

    OrderEntity toCreateEntity(OrderCreateDto orderDto);

    OrderDto toDto(OrderEntity orderEntity);

    List<OrderDto> toDtoList(List<OrderEntity> orderEntityList);

}

//
//import com.cats.spaceshop.domain.order.Order;
//import com.cats.spaceshop.domain.order.OrderEntry;
//import com.cats.spaceshop.domain.product.Product;
//import com.cats.spaceshop.dto.order.OrderCreateDto;
//import com.cats.spaceshop.dto.order.OrderDto;
//import com.cats.spaceshop.dto.order.OrderEntryDto;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component
//public class OrderMapper {
//
//    public Order toDomain(OrderCreateDto dto) {
//        return Order.builder()
//                .Id(UUID.randomUUID()) // Генеруємо ID замовлення
//                .cosmoCatId(dto.getCosmoCatId())
//                .entriesList(
//                        dto.getEntriesList().stream()
//                                .map(entry -> OrderEntry.builder()
//                                        .product(Product.builder().productId(entry.getProductId()).build()) // Dummy product
//                                        .quantity(entry.getQuantity())
//                                        .build()
//                                ).toList()
//                ).build();
//    }
//
//    public OrderDto toDto(Order domain) {
//        return OrderDto.builder()
//                .id(domain.getId())
//                .cosmoCatId(domain.getCosmoCatId())
//                .entriesList(
//                        domain.getEntriesList().stream()
//                                .map(entry -> OrderEntryDto.builder()
//                                        .productId(entry.getProduct().getProductId())
//                                        .productName(entry.getProduct().getName())
//                                        .productPrice(entry.getProduct().getPrice())
//                                        .quantity(entry.getQuantity())
//                                        .build()
//                                ).toList()
//                ).build();
//    }
//}
