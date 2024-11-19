package com.cats.spaceshop.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtendedValidationTest {

    @Test
    void testAlwaysTrue() {
        assertTrue(ExtendedValidation.alwaysTrue(), "The alwaysTrue method should return true.");
    }
}
