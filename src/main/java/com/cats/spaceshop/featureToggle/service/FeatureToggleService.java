package com.cats.spaceshop.featureToggle.service;

import com.cats.spaceshop.featureToggle.config.FeatureToggleProperties;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class FeatureToggleService {

    private final ConcurrentHashMap<String, Boolean> featureToggles;

    public FeatureToggleService(FeatureToggleProperties featureToggleProperties) {
        featureToggles = new ConcurrentHashMap<>(featureToggleProperties.getToggles());
    }

    public boolean check(String featureName) {
        return featureToggles.getOrDefault(featureName, false);
    }

    public void enable(String featureName) {
        featureToggles.put(featureName, true);
    }

    public void disable(String featureName) {
        featureToggles.put(featureName, false);
    }
}

//package com.cats.spaceshop.featureToggle.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FeatureToggleService {
//
//    @Value("${feature.cosmoCats.enabled}")
//    private boolean cosmoCatsEnabled;
//
//    @Value("${feature.kittyProducts.enabled}")
//    private boolean kittyProductsEnabled;
//
//    public boolean isCosmoCatsEnabled() {
//        return cosmoCatsEnabled;
//    }
//
//    public boolean isKittyProductsEnabled() {
//        return kittyProductsEnabled;
//    }
//}