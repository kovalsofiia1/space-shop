package com.cats.spaceshop.domain.shoppingCart;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import com.cats.spaceshop.domain.product.Product;

@Value
@Builder(toBuilder = true)
public class ShoppingCart {
    UUID cartId;
    String customerId;
    List<Product> products;
    double totalPrice;
}