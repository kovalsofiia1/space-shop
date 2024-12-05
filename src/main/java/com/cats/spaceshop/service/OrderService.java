package com.cats.spaceshop.service;

import com.cats.spaceshop.dto.order.OrderCreateDto;
import com.cats.spaceshop.dto.order.OrderDto;
import com.cats.spaceshop.dto.order.OrderEntryCreateDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto createOrder(OrderCreateDto orderCreateDto);
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(UUID orderId);
    OrderDto addEntryToOrder(UUID orderId, OrderEntryCreateDto entryCreateDto);
    OrderDto deleteEntryFromOrder(UUID orderId, UUID productId);
}
