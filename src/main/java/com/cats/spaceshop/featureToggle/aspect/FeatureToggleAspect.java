package com.cats.spaceshop.featureToggle.aspect;

import com.cats.spaceshop.featureToggle.config.FeatureToggleProperties;
import com.cats.spaceshop.featureToggle.service.FeatureToggleService;
import com.cats.spaceshop.featureToggle.FeatureToggles;
import com.cats.spaceshop.featureToggle.annotation.FeatureToggle;
import com.cats.spaceshop.featureToggle.exception.FeatureToggleNotEnabledException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Around(value = "@annotation(featureToggle)")
    public Object checkFeatureToggleAnnotation(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        return checkToggle(joinPoint, featureToggle);
    }

    private Object checkToggle(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        FeatureToggles toggle = featureToggle.value();

        if (featureToggleService.check(toggle.getFeatureName())) {
            return joinPoint.proceed();
        }
        log.warn("Feature toggle {} is not enabled!", toggle.getFeatureName());
        throw new FeatureToggleNotEnabledException(toggle.getFeatureName());
    }

}


//package com.cats.spaceshop.featureToggle.aspect;
//
//import com.cats.spaceshop.featureToggle.service.FeatureToggleService;
//import com.cats.spaceshop.featureToggle.exception.FeatureToggleNotEnabledException;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class FeatureToggleAspect {
//
//    private final FeatureToggleService featureToggleService;
//
//    public FeatureToggleAspect(FeatureToggleService featureToggleService) {
//        this.featureToggleService = featureToggleService;
//    }
//
//    @Around("execution(public String com.cats.spaceshop.service.CosmoCatService.getCosmoCats())")
//    public Object checkCosmoCatsFeatureToggle(ProceedingJoinPoint joinPoint) throws Throwable {
//        boolean isCosmoCatsEnabled = featureToggleService.isCosmoCatsEnabled();
//
//        System.out.println("Checking feature toggle for cosmoCats - Enabled: " + isCosmoCatsEnabled);
//
//        if (isCosmoCatsEnabled) {
//            return joinPoint.proceed();  // Proceed with the method execution
//        } else {
//            throw new FeatureToggleNotEnabledException("cosmoCats");
//        }
//    }
//}