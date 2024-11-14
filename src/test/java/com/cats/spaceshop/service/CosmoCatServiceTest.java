package com.cats.spaceshop.service;

import com.cats.spaceshop.featureToggle.service.FeatureToggleService;
import com.cats.spaceshop.featureToggle.exception.FeatureToggleNotEnabledException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CosmoCatServiceTest {

    @Mock
    private FeatureToggleService featureToggleService;

    @InjectMocks
    private CosmoCatService cosmoCatService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks manually if not using a test runner
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCosmoCats_whenFeatureEnabled() {
        // Given: The feature is enabled
        when(featureToggleService.isCosmoCatsEnabled()).thenReturn(true);

        // When: The getCosmoCats method is called
        String result = cosmoCatService.getCosmoCats();

        // Then: The result should be the expected response
        assertEquals("List of Cosmo Cats!", result);
    }

    @Test
    public void testGetCosmoCats_whenFeatureDisabled() {
        // Given: The feature is disabled
        when(featureToggleService.isCosmoCatsEnabled()).thenReturn(false);

        // Then: The FeatureToggleNotEnabledException should be thrown
        assertThrows(FeatureToggleNotEnabledException.class, () -> {
            // When: The getCosmoCats method is called
            cosmoCatService.getCosmoCats();
        });
    }
}
