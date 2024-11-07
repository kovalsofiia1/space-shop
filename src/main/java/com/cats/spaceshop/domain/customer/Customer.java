package com.cats.spaceshop.domain.customer;

import lombok.Builder;
import lombok.Value;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Customer {

    UUID id;
    String name;
    String email;
    String phoneNumber;
    String address;
}
