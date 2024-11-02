package com.cats.spaceshop.dto.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CosmicWordValidatorTest {

    private CosmicWordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CosmicWordValidator();
        validator.initialize(null); // Initialization if necessary
    }

    @Test
    void testValidCosmicTerm() {
        assertTrue(validator.isValid("This is a star!", null));
        assertTrue(validator.isValid("Galactic adventures await.", null));
        assertTrue(validator.isValid("The universe is vast.", null));
        assertTrue(validator.isValid("I saw a meteor today.", null));
    }

    @Test
    void testInvalidCosmicTerm() {
        assertFalse(validator.isValid("This is a cat.", null));
        assertFalse(validator.isValid("Earth is our home.", null));
        assertFalse(validator.isValid("Just a simple sentence.", null));
    }

    @Test
    void testNullValue() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void testEmptyString() {
        assertFalse(validator.isValid("", null));
    }
}
