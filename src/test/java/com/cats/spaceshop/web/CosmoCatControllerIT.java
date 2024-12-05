package com.cats.spaceshop.web;

import com.cats.spaceshop.AbstractIt;
import com.cats.spaceshop.dto.cosmocat.CosmoCatCreateDto;
import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;
import com.cats.spaceshop.repository.CustomerRepository;
import com.cats.spaceshop.repository.entity.CustomerEntity;
import com.cats.spaceshop.service.CosmoCatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
@DisplayName("CosmoCat Controller Integration Test with Service Logic")
public class CosmoCatControllerIT extends AbstractIt {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired @SpyBean private CustomerRepository cosmoCatRepository;
    @Autowired @SpyBean private CosmoCatService cosmoCatService;

    private CustomerEntity cosmoCatEntity;
    private CosmoCatCreateDto cosmoCatCreateDto;

    @BeforeEach
    void setUp() {
        cosmoCatEntity = CustomerEntity.builder()
                .name("Luna")
                .email("luna@cosmocats.com")
                .phoneNumber("(123) 456-7890") // Example phone number in the required format
                .address("123 Cosmic Street, Galaxy Town") // Example address
                .build();

        cosmoCatEntity = cosmoCatRepository.save(cosmoCatEntity);

        cosmoCatCreateDto = CosmoCatCreateDto.builder()
                .name("Luna")
                .email("luna@cosmocats.com")
                .phoneNumber("(123) 456-7890") // Same phone number format as above
                .address("123 Cosmic Street, Galaxy Town") // Same address as above
                .build();
    }

    @AfterEach
    void cleanUp() {
        cosmoCatRepository.deleteAll();
    }

    @Test
    void shouldReturnAllCosmoCats() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(cosmoCatEntity.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("Luna"));
    }

    @Test
    void shouldReturnCosmoCatById() throws Exception {
        UUID cosmoCatId = cosmoCatEntity.getId();

        mockMvc.perform(get("/api/v1/cosmo-cats/{id}", cosmoCatId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cosmoCatId.toString()))
                .andExpect(jsonPath("$.name").value("Luna"));
    }

    @Test
    void shouldReturn404WhenCosmoCatNotFoundById() throws Exception {
        UUID nonExistingId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/cosmo-cats/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Cosmo cat with id " + nonExistingId + " not found exception"));
    }

    @Test
    void shouldReturnCosmoCatByEmail() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats/email")
                        .param("email", cosmoCatEntity.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("luna@cosmocats.com"));
    }

    @Test
    void shouldReturn404WhenCosmoCatNotFoundByEmail() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats/email")
                        .param("email", "unknown@cosmocats.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Cosmo cat with email unknown@cosmocats.com not found exception"));
    }

    @Test
    void shouldAddCosmoCat() throws Exception {
        CosmoCatCreateDto newCosmoCat = CosmoCatCreateDto.builder()
                .name("Luna New")
                .email("luna1@cosmocats.com")
                .phoneNumber("(123) 456-7890")
                .address("123 Cosmic Street, Galaxy Town")
                .build();

        mockMvc.perform(post("/api/v1/cosmo-cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCosmoCat)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Luna New"))
                .andExpect(jsonPath("$.phoneNumber").value("(123) 456-7890"));
    }

    @Test
    void shouldReturn400WhenAddingCosmoCatWithDuplicateEmail() throws Exception {
        mockMvc.perform(post("/api/v1/cosmo-cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cosmoCatCreateDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("Cosmo cat with email luna@cosmocats.com already exists exception"));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingCosmoCat() throws Exception {
        UUID nonExistingId = UUID.randomUUID();
        CosmoCatDto updateDto = CosmoCatDto.builder()
                .id(nonExistingId)
                .name("NonExisting Cat")
                .email("updatedluna1@cosmocats.com")
                .phoneNumber("(123) 456-7890")
                .address("123 Cosmic Street, Galaxy Town")
                .build();

        mockMvc.perform(put("/api/v1/cosmo-cats/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Cosmo cat with id " + nonExistingId + " not found exception"));
    }

    @Test
    void shouldDeleteCosmoCat() throws Exception {
        UUID cosmoCatId = cosmoCatEntity.getId();

        mockMvc.perform(delete("/api/v1/cosmo-cats/{id}", cosmoCatId))
                .andExpect(status().isNoContent());

        Assertions.assertFalse(cosmoCatRepository.existsById(cosmoCatId));
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingCosmoCat() throws Exception {
        UUID nonExistingId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/cosmo-cats/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Cosmo cat with id " + nonExistingId + " not found exception"));
    }
}





//    @Test
//    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
//    void shouldGet404FeatureDisabled() throws Exception {
//        mockMvc.perform(get("/api/v1/cosmo-cats")).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
//    void shouldGet200() throws Exception {
//        mockMvc.perform(get("/api/v1/cosmo-cats")).andExpect(status().isOk());
//    }


