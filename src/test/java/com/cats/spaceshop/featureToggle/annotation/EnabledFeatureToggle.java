package com.cats.spaceshop.featureToggle.annotation;

import com.cats.spaceshop.featureToggle.FeatureToggles;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface EnabledFeatureToggle {
    FeatureToggles value();
}