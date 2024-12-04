package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.dto.cosmocat.CosmoCatCreateDto;
import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;
import com.cats.spaceshop.repository.CustomerRepository;
import com.cats.spaceshop.repository.entity.CustomerEntity;
import com.cats.spaceshop.service.exception.CosmoCatNotFoundByEmailException;
import com.cats.spaceshop.service.exception.CosmoCatNotFoundException;
import com.cats.spaceshop.service.exception.CosmoCatWithEmailAlreadyExistsException;
import com.cats.spaceshop.service.mapper.CosmoCatMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CosmoCatServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CosmoCatMapper cosmoCatMapper;

    @InjectMocks
    private CosmoCatServiceImpl cosmoCatService;

    private UUID cosmoCatId;
    private CustomerEntity customerEntity;
    private CosmoCatDto cosmoCatDto;
    private CosmoCatCreateDto cosmoCatCreateDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cosmoCatId = UUID.randomUUID();
        customerEntity = CustomerEntity.builder()
                .id(cosmoCatId)
                .name("Test CosmoCat")
                .email("test@cosmocat.com")
                .phoneNumber("123456789")
                .address("Cosmo Street, Galaxy")
                .build();

        cosmoCatDto = CosmoCatDto.builder()
                .id(cosmoCatId)
                .name("Test CosmoCat")
                .email("test@cosmocat.com")
                .phoneNumber("123456789")
                .address("Cosmo Street, Galaxy")
                .build();

        cosmoCatCreateDto = CosmoCatCreateDto.builder()
                .name("Test CosmoCat")
                .email("test@cosmocat.com")
                .phoneNumber("123456789")
                .address("Cosmo Street, Galaxy")
                .build();
    }

    @Test
    void testGetCosmoCats() {
        List<CustomerEntity> entities = List.of(customerEntity);
        List<CosmoCatDto> dtos = List.of(cosmoCatDto);

        when(customerRepository.findAll()).thenReturn(entities);
        when(cosmoCatMapper.entitiesToDtos(entities)).thenReturn(dtos);

        List<CosmoCatDto> result = cosmoCatService.getCosmoCats();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cosmoCatDto, result.get(0));
        verify(customerRepository, times(1)).findAll();
        verify(cosmoCatMapper, times(1)).entitiesToDtos(entities);
    }

    @Test
    void testGetCosmoCatById() {
        when(customerRepository.findById(cosmoCatId)).thenReturn(Optional.of(customerEntity));
        when(cosmoCatMapper.entityToDto(customerEntity)).thenReturn(cosmoCatDto);

        CosmoCatDto result = cosmoCatService.getCosmoCatById(cosmoCatId);

        assertNotNull(result);
        assertEquals(cosmoCatDto, result);
        verify(customerRepository, times(1)).findById(cosmoCatId);
        verify(cosmoCatMapper, times(1)).entityToDto(customerEntity);
    }

    @Test
    void testGetCosmoCatById_NotFound() {
        when(customerRepository.findById(cosmoCatId)).thenReturn(Optional.empty());

        CosmoCatNotFoundException exception = assertThrows(CosmoCatNotFoundException.class, () -> {
            cosmoCatService.getCosmoCatById(cosmoCatId);
        });

        assertEquals("Cosmo cat with id " + cosmoCatId + " not found exception", exception.getMessage());
        verify(customerRepository, times(1)).findById(cosmoCatId);
    }

    @Test
    void testGetCosmoCatByEmail() {
        String email = "test@cosmocat.com";

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customerEntity));
        when(cosmoCatMapper.entityToDto(customerEntity)).thenReturn(cosmoCatDto);

        CosmoCatDto result = cosmoCatService.getCosmoCatByEmail(email);

        assertNotNull(result);
        assertEquals(cosmoCatDto, result);
        verify(customerRepository, times(1)).findByEmail(email);
        verify(cosmoCatMapper, times(1)).entityToDto(customerEntity);
    }

    @Test
    void testGetCosmoCatByEmail_NotFound() {
        String email = "notfound@cosmocat.com";

        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        CosmoCatNotFoundByEmailException exception = assertThrows(CosmoCatNotFoundByEmailException.class, () -> {
            cosmoCatService.getCosmoCatByEmail(email);
        });

        assertEquals("Cosmo cat with email notfound@cosmocat.com not found exception", exception.getMessage());
        verify(customerRepository, times(1)).findByEmail(email);
    }

    @Test
    void testAddCosmoCat() {
        when(customerRepository.findByEmail(cosmoCatCreateDto.getEmail())).thenReturn(Optional.empty());
        when(cosmoCatMapper.creationDtoToEntity(cosmoCatCreateDto)).thenReturn(customerEntity);
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(cosmoCatMapper.entityToDto(customerEntity)).thenReturn(cosmoCatDto);

        CosmoCatDto result = cosmoCatService.addCosmoCat(cosmoCatCreateDto);

        assertNotNull(result);
        assertEquals(cosmoCatDto, result);
        verify(customerRepository, times(1)).findByEmail(cosmoCatCreateDto.getEmail());
        verify(customerRepository, times(1)).save(customerEntity);
        verify(cosmoCatMapper, times(1)).creationDtoToEntity(cosmoCatCreateDto);
        verify(cosmoCatMapper, times(1)).entityToDto(customerEntity);
    }

    @Test
    void testAddCosmoCat_EmailAlreadyExists() {
        when(customerRepository.findByEmail(cosmoCatCreateDto.getEmail())).thenReturn(Optional.of(customerEntity));

        CosmoCatWithEmailAlreadyExistsException exception = assertThrows(CosmoCatWithEmailAlreadyExistsException.class, () -> {
            cosmoCatService.addCosmoCat(cosmoCatCreateDto);
        });

        assertEquals("Cosmo cat with email test@cosmocat.com already exists exception", exception.getMessage());
        verify(customerRepository, times(1)).findByEmail(cosmoCatCreateDto.getEmail());
    }

    @Test
    void testUpdateCosmoCat() {
        when(customerRepository.findById(cosmoCatId)).thenReturn(Optional.of(customerEntity));
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(cosmoCatMapper.entityToDto(customerEntity)).thenReturn(cosmoCatDto);

        CosmoCatDto result = cosmoCatService.updateCosmoCat(cosmoCatId, cosmoCatDto);

        assertNotNull(result);
        assertEquals(cosmoCatDto, result);
        verify(customerRepository, times(1)).findById(cosmoCatId);
        verify(customerRepository, times(1)).save(customerEntity);
        verify(cosmoCatMapper, times(1)).entityToDto(customerEntity);
    }

    @Test
    void testDeleteCosmoCat() {
        when(customerRepository.existsById(cosmoCatId)).thenReturn(true);

        cosmoCatService.deleteCosmoCat(cosmoCatId);

        verify(customerRepository, times(1)).existsById(cosmoCatId);
        verify(customerRepository, times(1)).deleteById(cosmoCatId);
    }

    @Test
    void testDeleteCosmoCat_NotFound() {
        when(customerRepository.existsById(cosmoCatId)).thenReturn(false);

        CosmoCatNotFoundException exception = assertThrows(CosmoCatNotFoundException.class, () -> {
            cosmoCatService.deleteCosmoCat(cosmoCatId);
        });

        assertEquals("Cosmo cat with id " + cosmoCatId + " not found exception", exception.getMessage());
        verify(customerRepository, times(1)).existsById(cosmoCatId);
    }
}
