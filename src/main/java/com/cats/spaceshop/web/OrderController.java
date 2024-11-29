package com.cats.spaceshop.web;

import com.cats.spaceshop.dto.order.OrderCreateDto;
import com.cats.spaceshop.dto.order.OrderDto;
import com.cats.spaceshop.dto.order.OrderEntryCreateDto;
import com.cats.spaceshop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        OrderDto createdOrder = orderService.createOrder(orderCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/entries")
    public ResponseEntity<OrderDto> addEntryToOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderEntryCreateDto entryCreateDto) {
        OrderDto updatedOrder = orderService.addEntryToOrder(orderId, entryCreateDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}/entries/{productId}")
    public ResponseEntity<OrderDto> deleteEntryFromOrder(
            @PathVariable UUID orderId,
            @PathVariable UUID productId) {
        OrderDto updatedOrder = orderService.deleteEntryFromOrder(orderId, productId);
        return ResponseEntity.ok(updatedOrder);
    }
}