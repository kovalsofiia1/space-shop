package com.cats.spaceshop.service.mapper;

import com.cats.spaceshop.dto.cosmocat.CosmoCatCreateDto;
import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;
import com.cats.spaceshop.repository.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CosmoCatMapper {

    CustomerEntity creationDtoToEntity(CosmoCatCreateDto creationDto);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    CosmoCatDto creationDtoToDto(CosmoCatCreateDto creationDto);

    CosmoCatDto entityToDto(CustomerEntity customerEntity);

    List<CosmoCatDto> entitiesToDtos(List<CustomerEntity> customerEntities);

    CustomerEntity dtoToEntity(CosmoCatDto dto);

    List<CustomerEntity> dtosToEntities(List<CosmoCatDto> dtos);
}