package com.cats.spaceshop.web;

import com.cats.spaceshop.dto.cosmocat.CosmoCatCreateDto;
import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;
import com.cats.spaceshop.service.CosmoCatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cosmo-cats")
public class CosmoCatController {

    private final CosmoCatService cosmoCatService;

    public CosmoCatController(CosmoCatService cosmoCatService) {
        this.cosmoCatService = cosmoCatService;
    }

    @GetMapping
    public ResponseEntity<List<CosmoCatDto>> getAllCosmoCats() {
        List<CosmoCatDto> cosmoCats = cosmoCatService.getCosmoCats();
        return ResponseEntity.ok(cosmoCats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CosmoCatDto> getCosmoCatById(@PathVariable UUID id) {
        CosmoCatDto cosmoCat = cosmoCatService.getCosmoCatById(id);
        return ResponseEntity.ok(cosmoCat);
    }

    @PostMapping
    public ResponseEntity<CosmoCatDto> addCosmoCat(@RequestBody CosmoCatCreateDto cosmoCatDto) {
        CosmoCatDto createdCosmoCat = cosmoCatService.addCosmoCat(cosmoCatDto);
        return ResponseEntity.ok(createdCosmoCat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CosmoCatDto> updateCosmoCat(@PathVariable UUID id, @RequestBody CosmoCatDto cosmoCatDto) {
        CosmoCatDto updatedCosmoCat = cosmoCatService.updateCosmoCat(id, cosmoCatDto);
        return ResponseEntity.ok(updatedCosmoCat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCosmoCat(@PathVariable UUID id) {
        cosmoCatService.deleteCosmoCat(id);
        return ResponseEntity.noContent().build();
    }
}