package com.cats.spaceshop.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {

    private static final List<String> COSMIC_TERMS = Arrays.asList(
            "star", "galaxy", "comet", "planet", "asteroid", "nebula",
            "meteor", "quasar", "supernova", "orbit", "cosmos",
            "universe", "blackhole", "constellation", "satellite",
            "spaceship", "rocket", "spacesuit", "meteorite", "eclipse",
            "starlight", "wormhole", "interstellar", "astronaut",
            "telescope", "milkyway", "exoplanet", "solar", "lunar", "space", "ship"
    );
    @Override
    public void initialize(CosmicWordCheck constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull should be used to enforce non-null if required
        }
        return COSMIC_TERMS.stream().anyMatch(value.toLowerCase()::contains);
    }
}