package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.domain.cosmocat.CosmoCat;
import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CosmoCatMapper {

    CosmoCatDto toDto(CosmoCat category);

    CosmoCat toEntry(CosmoCatDto category);
}