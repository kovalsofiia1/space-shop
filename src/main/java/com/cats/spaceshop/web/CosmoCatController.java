package com.cats.spaceshop.web;

import com.cats.spaceshop.service.CosmoCatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cosmo-cats")
public class CosmoCatController {

    private final CosmoCatService cosmoCatService;

    public CosmoCatController(CosmoCatService cosmoCatService) {
        this.cosmoCatService = cosmoCatService;
    }

    @GetMapping
    public ResponseEntity<String> getCosmoCats() {
        return ResponseEntity.ok(cosmoCatService.getCosmoCats());
    }
}