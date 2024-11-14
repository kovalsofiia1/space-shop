package com.cats.spaceshop.web;

import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;
import com.cats.spaceshop.featureToggle.FeatureToggles;
import com.cats.spaceshop.featureToggle.annotation.FeatureToggle;
import com.cats.spaceshop.service.CosmoCatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cosmo-cats")
public class CosmoCatController {

    private final CosmoCatService cosmoCatService;

    public CosmoCatController(CosmoCatService cosmoCatService) {
        this.cosmoCatService = cosmoCatService;
    }

    @GetMapping
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public ResponseEntity<List<CosmoCatDto>> getAllCosmoCats() {
        List<CosmoCatDto> cosmoCats = cosmoCatService.getCosmoCats();
        return ResponseEntity.ok(cosmoCats);
    }
}
