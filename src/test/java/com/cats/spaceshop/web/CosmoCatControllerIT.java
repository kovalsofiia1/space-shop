package com.cats.spaceshop.web;

import static org.junit.jupiter.api.Assertions.*;

import com.cats.spaceshop.AbstractIt;
import com.cats.spaceshop.featureToggle.FeatureToggleExtension;
import com.cats.spaceshop.featureToggle.FeatureToggles;
import com.cats.spaceshop.featureToggle.annotation.DisabledFeatureToggle;
import com.cats.spaceshop.featureToggle.annotation.EnabledFeatureToggle;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DisplayName("Cosmo Cats Controller IT")
@ExtendWith(FeatureToggleExtension.class)
class CosmoCatControllerIT extends AbstractIt {

//    private static final String DOBBY = "dobby";
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldGet404FeatureDisabled() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats")).andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldGet200() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats")).andExpect(status().isOk());
    }
}

