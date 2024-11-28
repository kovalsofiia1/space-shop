package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.cosmocat.CosmoCat;
import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CosmoCatMapperTest {

    private CosmoCatMapper cosmoCatMapper;

    @BeforeEach
    void setUp() {
        cosmoCatMapper = Mappers.getMapper(CosmoCatMapper.class);
    }

    @Test
    void shouldMapCosmoCatToCosmoCatDto() {
        UUID id = UUID.randomUUID();
        CosmoCat cosmoCat = CosmoCat.builder()
                .id(id)
                .name("Cosmo")
                .email("cosmo@example.com")
                .phoneNumber("(123) 456-7890")
                .address("Galaxy Road 42")
                .build();

        CosmoCatDto cosmoCatDto = cosmoCatMapper.toDto(cosmoCat);

        assertNotNull(cosmoCatDto);
        assertEquals(cosmoCat.getId(), cosmoCatDto.getId());
        assertEquals(cosmoCat.getName(), cosmoCatDto.getName());
        assertEquals(cosmoCat.getEmail(), cosmoCatDto.getEmail());
        assertEquals(cosmoCat.getPhoneNumber(), cosmoCatDto.getPhoneNumber());
        assertEquals(cosmoCat.getAddress(), cosmoCatDto.getAddress());
    }

    @Test
    void shouldMapCosmoCatDtoToCosmoCat() {
        UUID id = UUID.randomUUID();
        CosmoCatDto cosmoCatDto = CosmoCatDto.builder()
                .id(id)
                .name("Cosmo")
                .email("cosmo@example.com")
                .phoneNumber("(123) 456-7890")
                .address("Galaxy Road 42")
                .build();

        CosmoCat cosmoCat = cosmoCatMapper.toEntry(cosmoCatDto);

        assertNotNull(cosmoCat);
        assertEquals(cosmoCatDto.getId(), cosmoCat.getId());
        assertEquals(cosmoCatDto.getName(), cosmoCat.getName());
        assertEquals(cosmoCatDto.getEmail(), cosmoCat.getEmail());
        assertEquals(cosmoCatDto.getPhoneNumber(), cosmoCat.getPhoneNumber());
        assertEquals(cosmoCatDto.getAddress(), cosmoCat.getAddress());
    }
}
