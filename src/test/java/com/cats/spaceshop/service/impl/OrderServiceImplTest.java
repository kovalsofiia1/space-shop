package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.dto.order.OrderCreateDto;
import com.cats.spaceshop.dto.order.OrderDto;
import com.cats.spaceshop.dto.order.OrderEntryCreateDto;
import com.cats.spaceshop.repository.*;
import com.cats.spaceshop.repository.entity.*;
import com.cats.spaceshop.service.exception.CosmoCatNotFoundException;
import com.cats.spaceshop.service.exception.OrderNotFoundException;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import com.cats.spaceshop.service.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEntryRepository orderEntryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private UUID orderId;
    private UUID productId;
    private UUID cosmoCatId;
    private OrderEntity orderEntity;
    private OrderDto orderDto;
    private ProductEntity productEntity;
    private CustomerEntity customerEntity;
    private OrderCreateDto orderCreateDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        orderId = UUID.randomUUID();
        productId = UUID.randomUUID();
        cosmoCatId = UUID.randomUUID();

        customerEntity = CustomerEntity.builder()
                .id(cosmoCatId)
                .name("Cosmo Cat")
                .build();

        productEntity = ProductEntity.builder()
                .id(productId)
                .name("Space Widget")
                .price(BigDecimal.valueOf(99.99))
                .stockQuantity(100)
                .build();

        orderEntity = OrderEntity.builder()
                .id(orderId)
                .createdAt(LocalDateTime.now())
                .customer(customerEntity)
                .build();

        orderDto = OrderDto.builder()
                .id(orderId)
                .cosmoCatId(cosmoCatId)
                .entriesList(List.of())
                .build();

        orderCreateDto = OrderCreateDto.builder()
                .cosmoCatId(cosmoCatId)
                .entriesList(List.of(OrderEntryCreateDto.builder()
                        .productId(productId)
                        .quantity(2)
                        .build()))
                .build();
    }

    @Test
    void testCreateOrder() {
        when(customerRepository.findById(cosmoCatId)).thenReturn(Optional.of(customerEntity));
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        OrderDto result = orderService.createOrder(orderCreateDto);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        verify(customerRepository, times(1)).findById(cosmoCatId);
        verify(productRepository, times(1)).findById(productId);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(orderEntryRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testCreateOrder_CustomerNotFound() {
        when(customerRepository.findById(cosmoCatId)).thenReturn(Optional.empty());

        assertThrows(CosmoCatNotFoundException.class, () -> orderService.createOrder(orderCreateDto));
        verify(customerRepository, times(1)).findById(cosmoCatId);
    }

    @Test
    void testCreateOrder_ProductNotFound() {
        when(customerRepository.findById(cosmoCatId)).thenReturn(Optional.of(customerEntity));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(orderCreateDto));
        verify(customerRepository, times(1)).findById(cosmoCatId);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(orderEntity));

        List<OrderDto> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));

        OrderDto result = orderService.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(orderId));
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testAddEntryToOrder() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(orderEntryRepository.save(any(OrderEntryEntity.class))).thenReturn(new OrderEntryEntity());

        OrderDto result = orderService.addEntryToOrder(orderId, OrderEntryCreateDto.builder()
                .productId(productId)
                .quantity(3)
                .build());

        assertNotNull(result);
        verify(orderRepository, times(2)).findById(orderId);
        verify(productRepository, times(1)).findById(productId);
        verify(orderEntryRepository, times(1)).save(any(OrderEntryEntity.class));
    }

    @Test
    void testDeleteEntryFromOrder() {
        OrderEntryEntity entry = OrderEntryEntity.builder()
                .order(orderEntity)
                .product(productEntity)
                .quantity(1)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderEntryRepository.findByOrderIdAndProductId(orderId, productId)).thenReturn(Optional.of(entry));

        OrderDto result = orderService.deleteEntryFromOrder(orderId, productId);

        assertNotNull(result);
        verify(orderRepository, times(2)).findById(orderId);
        verify(orderEntryRepository, times(1)).findByOrderIdAndProductId(orderId, productId);
        verify(orderEntryRepository, times(1)).delete(entry);
    }

    @Test
    void testDeleteEntryFromOrder_EntryNotFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderEntryRepository.findByOrderIdAndProductId(orderId, productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> orderService.deleteEntryFromOrder(orderId, productId));
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderEntryRepository, times(1)).findByOrderIdAndProductId(orderId, productId);
    }
}
