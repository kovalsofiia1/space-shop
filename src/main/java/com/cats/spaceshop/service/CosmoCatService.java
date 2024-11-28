package com.cats.spaceshop.service;

import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;

import java.util.List;

public interface CosmoCatService {
    List<CosmoCatDto> getCosmoCats();
}