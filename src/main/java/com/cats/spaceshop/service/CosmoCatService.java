package com.cats.spaceshop.service;

import com.cats.spaceshop.dto.cosmocat.CosmoCatCreateDto;
import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;

import java.util.List;
import java.util.UUID;

public interface CosmoCatService {
    List<CosmoCatDto> getCosmoCats();
    CosmoCatDto getCosmoCatById(UUID id);
    CosmoCatDto addCosmoCat(CosmoCatCreateDto cosmoCatDto);
    CosmoCatDto updateCosmoCat(UUID id, CosmoCatDto cosmoCatDto);
    void deleteCosmoCat(UUID id);
}