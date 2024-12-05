package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.dto.order.OrderCreateDto;
import com.cats.spaceshop.dto.order.OrderDto;
import com.cats.spaceshop.dto.order.OrderEntryCreateDto;
import com.cats.spaceshop.dto.order.OrderEntryDto;
import com.cats.spaceshop.repository.OrderRepository;
import com.cats.spaceshop.repository.OrderEntryRepository;
import com.cats.spaceshop.repository.ProductRepository;
import com.cats.spaceshop.repository.CustomerRepository;
import com.cats.spaceshop.repository.entity.OrderEntity;
import com.cats.spaceshop.repository.entity.OrderEntryEntity;
import com.cats.spaceshop.repository.entity.ProductEntity;
import com.cats.spaceshop.service.OrderService;
import com.cats.spaceshop.service.exception.CosmoCatNotFoundException;
import com.cats.spaceshop.service.exception.OrderNotFoundException;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import com.cats.spaceshop.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEntryRepository orderEntryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto createOrder(OrderCreateDto orderCreateDto) {

        var customer = customerRepository.findById(orderCreateDto.getCosmoCatId())
                .orElseThrow(() -> new CosmoCatNotFoundException(orderCreateDto.getCosmoCatId()));

        OrderEntity orderEntity = OrderEntity.builder()
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .build();
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        List<OrderEntryEntity> orderEntries = orderCreateDto.getEntriesList().stream()
                .map(entry -> {
                    ProductEntity productEntity = productRepository.findById(entry.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException(entry.getProductId()));

                    return OrderEntryEntity.builder()
                            .order(savedOrderEntity)
                            .product(productEntity)
                            .quantity(entry.getQuantity())
                            .build();
                })
                .toList();

        orderEntryRepository.saveAll(orderEntries);

        return transformToOrderDto(savedOrderEntity);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::transformToOrderDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return transformToOrderDto(orderEntity);
    }

    @Override
    @Transactional
    public OrderDto addEntryToOrder(UUID orderId, OrderEntryCreateDto entryCreateDto) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        ProductEntity productEntity = productRepository.findById(entryCreateDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(entryCreateDto.getProductId()));

        OrderEntryEntity orderEntryEntity = new OrderEntryEntity();
        orderEntryEntity.setOrder(orderEntity);
        orderEntryEntity.setProduct(productEntity);
        orderEntryEntity.setQuantity(entryCreateDto.getQuantity());

        orderEntryRepository.save(orderEntryEntity);

        return getOrderById(orderId);
    }

    @Override
    @Transactional
    public OrderDto deleteEntryFromOrder(UUID orderId, UUID productId) {

        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        OrderEntryEntity entryToRemove = orderEntryRepository.findByOrderIdAndProductId(orderId, productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        orderEntryRepository.delete(entryToRemove);

        return getOrderById(orderId);
    }

    public List<OrderEntryEntity> getEntriesForOrder(UUID orderId) {
        return orderEntryRepository.findByOrderId(orderId);
    }

    private OrderDto transformToOrderDto(OrderEntity orderEntity) {
        return OrderDto.builder()
                .id(orderEntity.getId())
                .cosmoCatId(orderEntity.getCustomer().getId())
                .entriesList(getEntriesForOrder(orderEntity.getId()).stream()
                        .map(orderEntryEntity -> OrderEntryDto.builder()
                                .productId(orderEntryEntity.getProduct().getId())
                                .productName(orderEntryEntity.getProduct().getName())
                                .productPrice(orderEntryEntity.getProduct().getPrice())
                                .quantity(orderEntryEntity.getQuantity())
                                .build())
                        .toList())
                .build();
    }

}
