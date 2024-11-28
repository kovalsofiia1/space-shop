package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;
import com.cats.spaceshop.service.CosmoCatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CosmoCatServiceImpl implements CosmoCatService {

    @Override
    public List<CosmoCatDto> getCosmoCats() {
        return List.of(
                CosmoCatDto.builder()
                        .id(UUID.randomUUID())
                        .name("Stellar Paws")
                        .email("stellar.paws@cosmocats.com")
                        .phoneNumber("(123) 456-7890")
                        .address("123 Cosmic Ave, Star City")
                        .build(),

                CosmoCatDto.builder()
                        .id(UUID.randomUUID())
                        .name("Galactic Whiskers")
                        .email("galactic.whiskers@cosmocats.com")
                        .phoneNumber("(234) 567-8901")
                        .address("456 Nebula St, Galaxy Town")
                        .build(),

                CosmoCatDto.builder()
                        .id(UUID.randomUUID())
                        .name("Nebula Claws")
                        .email("nebula.claws@cosmocats.com")
                        .phoneNumber("(345) 678-9012")
                        .address("789 Supernova Rd, Quasar City")
                        .build()
        );
    }
}
