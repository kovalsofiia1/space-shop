package com.cats.spaceshop.featureToggle.aspect;

import com.cats.spaceshop.featureToggle.FeatureToggleService;
import com.cats.spaceshop.featureToggle.annotation.FeatureToggle;
import com.cats.spaceshop.featureToggle.exception.FeatureToggleNotEnabledException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    public FeatureToggleAspect(FeatureToggleService featureToggleService) {
        this.featureToggleService = featureToggleService;
    }

    @Around("execution(public String com.cats.spaceshop.service.CosmoCatService.getCosmoCats())")
    public Object checkCosmoCatsFeatureToggle(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean isCosmoCatsEnabled = featureToggleService.isCosmoCatsEnabled();

        System.out.println("Checking feature toggle for cosmoCats - Enabled: " + isCosmoCatsEnabled);

        if (isCosmoCatsEnabled) {
            return joinPoint.proceed();  // Proceed with the method execution
        } else {
            throw new FeatureToggleNotEnabledException("cosmoCats");
        }
    }
}