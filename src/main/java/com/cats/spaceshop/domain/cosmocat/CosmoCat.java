package com.cats.spaceshop.domain.cosmocat;

import lombok.Builder;
import lombok.Value;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class CosmoCat {
    UUID id;
    String name;
    String email;
    String phoneNumber;
    String address;
}
