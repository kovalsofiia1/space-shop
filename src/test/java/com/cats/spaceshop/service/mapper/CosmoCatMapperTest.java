package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.dto.cosmocat.CosmoCatCreateDto;
import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;
import com.cats.spaceshop.repository.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CosmoCatMapperTest {

    private CosmoCatMapper cosmoCatMapper;

    @BeforeEach
    void setUp() {
        cosmoCatMapper = Mappers.getMapper(CosmoCatMapper.class);
    }

    @Test
    void shouldMapCosmoCatCreateDtoToCustomerEntity() {
        CosmoCatCreateDto createDto = CosmoCatCreateDto.builder()
                .name("Cosmo")
                .email("cosmo@example.com")
                .phoneNumber("(123) 456-7890")
                .address("Galaxy Road 42")
                .build();

        CustomerEntity customerEntity = cosmoCatMapper.creationDtoToEntity(createDto);

        assertNotNull(customerEntity);
        assertEquals(createDto.getName(), customerEntity.getName());
        assertEquals(createDto.getEmail(), customerEntity.getEmail());
        assertEquals(createDto.getPhoneNumber(), customerEntity.getPhoneNumber());
        assertEquals(createDto.getAddress(), customerEntity.getAddress());
    }

    @Test
    void shouldMapCustomerEntityToCosmoCatDto() {
        UUID id = UUID.randomUUID();
        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(id)
                .name("Cosmo")
                .email("cosmo@example.com")
                .phoneNumber("(123) 456-7890")
                .address("Galaxy Road 42")
                .build();

        CosmoCatDto cosmoCatDto = cosmoCatMapper.entityToDto(customerEntity);

        assertNotNull(cosmoCatDto);
        assertEquals(customerEntity.getId(), cosmoCatDto.getId());
        assertEquals(customerEntity.getName(), cosmoCatDto.getName());
        assertEquals(customerEntity.getEmail(), cosmoCatDto.getEmail());
        assertEquals(customerEntity.getPhoneNumber(), cosmoCatDto.getPhoneNumber());
        assertEquals(customerEntity.getAddress(), cosmoCatDto.getAddress());
    }

    @Test
    void shouldMapListOfCustomerEntitiesToListOfCosmoCatDtos() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        CustomerEntity customer1 = CustomerEntity.builder()
                .id(id1)
                .name("Cosmo")
                .email("cosmo@example.com")
                .phoneNumber("(123) 456-7890")
                .address("Galaxy Road 42")
                .build();

        CustomerEntity customer2 = CustomerEntity.builder()
                .id(id2)
                .name("Luna")
                .email("luna@example.com")
                .phoneNumber("(987) 654-3210")
                .address("Moon Street 101")
                .build();

        List<CosmoCatDto> dtos = cosmoCatMapper.entitiesToDtos(List.of(customer1, customer2));

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(customer1.getId(), dtos.get(0).getId());
        assertEquals(customer2.getId(), dtos.get(1).getId());
    }

    @Test
    void shouldMapCosmoCatDtoToCustomerEntity() {
        UUID id = UUID.randomUUID();
        CosmoCatDto dto = CosmoCatDto.builder()
                .id(id)
                .name("Cosmo")
                .email("cosmo@example.com")
                .phoneNumber("(123) 456-7890")
                .address("Galaxy Road 42")
                .build();

        CustomerEntity customerEntity = cosmoCatMapper.dtoToEntity(dto);

        assertNotNull(customerEntity);
        assertEquals(dto.getId(), customerEntity.getId());
        assertEquals(dto.getName(), customerEntity.getName());
        assertEquals(dto.getEmail(), customerEntity.getEmail());
        assertEquals(dto.getPhoneNumber(), customerEntity.getPhoneNumber());
        assertEquals(dto.getAddress(), customerEntity.getAddress());
    }

    @Test
    void shouldMapListOfCosmoCatDtosToListOfCustomerEntities() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        CosmoCatDto dto1 = CosmoCatDto.builder()
                .id(id1)
                .name("Cosmo")
                .email("cosmo@example.com")
                .phoneNumber("(123) 456-7890")
                .address("Galaxy Road 42")
                .build();

        CosmoCatDto dto2 = CosmoCatDto.builder()
                .id(id2)
                .name("Luna")
                .email("luna@example.com")
                .phoneNumber("(987) 654-3210")
                .address("Moon Street 101")
                .build();

        List<CustomerEntity> entities = cosmoCatMapper.dtosToEntities(List.of(dto1, dto2));

        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dto1.getId(), entities.get(0).getId());
        assertEquals(dto2.getId(), entities.get(1).getId());
    }
}
