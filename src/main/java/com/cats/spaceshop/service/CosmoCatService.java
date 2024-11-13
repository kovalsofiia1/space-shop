package com.cats.spaceshop.service;

import com.cats.spaceshop.featureToggle.annotation.FeatureToggle;
import org.springframework.stereotype.Service;

@Service
public class CosmoCatService {

    @FeatureToggle("feature.cosmoCats.enabled")
    public String getCosmoCats() {
        return "List of Cosmo Cats!";
    }
}
