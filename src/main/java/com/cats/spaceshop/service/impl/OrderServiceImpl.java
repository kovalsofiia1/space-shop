package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.domain.order.Order;
import com.cats.spaceshop.domain.order.OrderEntry;
import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.order.OrderCreateDto;
import com.cats.spaceshop.dto.order.OrderDto;
import com.cats.spaceshop.dto.order.OrderEntryCreateDto;
import com.cats.spaceshop.service.OrderService;
import com.cats.spaceshop.service.exception.OrderNotFoundException;
import com.cats.spaceshop.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    // Dummy storage for orders (замість бази даних)
    private final Map<UUID, Order> orderStorage = new HashMap<>();

    @Override
    public OrderDto createOrder(OrderCreateDto orderCreateDto) {
        Order order = orderMapper.toDomain(orderCreateDto);
        orderStorage.put(order.getId(), order);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderStorage.values().stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
        Order order = orderStorage.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto addEntryToOrder(UUID orderId, OrderEntryCreateDto entryCreateDto) {
        Order order = orderStorage.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }

        OrderEntry newEntry = OrderEntry.builder()
                .product(Product.builder().productId(entryCreateDto.getProductId()).build()) // Dummy product
                .quantity(entryCreateDto.getQuantity())
                .build();

        List<OrderEntry> updatedEntries = new ArrayList<>(order.getEntriesList());
        updatedEntries.add(newEntry);

        order = order.toBuilder().entriesList(updatedEntries).build();
        orderStorage.put(orderId, order);

        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto deleteEntryFromOrder(UUID orderId, UUID productId) {
        Order order = orderStorage.get(orderId);
        if (order == null) {
            throw new NoSuchElementException("Order not found with ID: " + orderId);
        }

        List<OrderEntry> updatedEntries = order.getEntriesList().stream()
                .filter(entry -> !entry.getProduct().getProductId().equals(productId))
                .toList();

        if (updatedEntries.size() == order.getEntriesList().size()) {
            throw new NoSuchElementException("Product not found in order: " + productId);
        }

        order = order.toBuilder().entriesList(updatedEntries).build();
        orderStorage.put(orderId, order);

        return orderMapper.toDto(order);
    }
}