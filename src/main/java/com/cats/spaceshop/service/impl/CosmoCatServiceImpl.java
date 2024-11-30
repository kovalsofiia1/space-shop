package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.dto.cosmocat.CosmoCatDto;
import com.cats.spaceshop.repository.CustomerRepository;
import com.cats.spaceshop.repository.entity.CustomerEntity;
import com.cats.spaceshop.service.CosmoCatService;
import com.cats.spaceshop.service.exception.CosmoCatNotFoundException;
import org.springframework.stereotype.Service;

import com.cats.spaceshop.dto.cosmocat.CosmoCatCreateDto;
import com.cats.spaceshop.service.mapper.CosmoCatMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CosmoCatServiceImpl implements CosmoCatService {

    private final CustomerRepository customerRepository;
    private final CosmoCatMapper cosmoCatMapper;

    public CosmoCatServiceImpl(CustomerRepository customerRepository, CosmoCatMapper cosmoCatMapper) {
        this.customerRepository = customerRepository;
        this.cosmoCatMapper = cosmoCatMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CosmoCatDto> getCosmoCats() {
        return cosmoCatMapper.entitiesToDtos(customerRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)  // Read-only transaction for fetching an entity
    public CosmoCatDto getCosmoCatById(UUID id) {
        return customerRepository.findById(id)
                .map(cosmoCatMapper::entityToDto)
                .orElseThrow(() -> new CosmoCatNotFoundException(id));
    }

    @Override
    @Transactional
    public CosmoCatDto addCosmoCat(CosmoCatCreateDto cosmoCatCreationDto) {
        CustomerEntity customerEntity = cosmoCatMapper.creationDtoToEntity(cosmoCatCreationDto);
        CustomerEntity savedEntity = customerRepository.save(customerEntity);
        return cosmoCatMapper.entityToDto(savedEntity);
    }

    @Override
    @Transactional
    public CosmoCatDto updateCosmoCat(UUID id, CosmoCatDto cosmoCatDto) {
        CustomerEntity existingEntity = customerRepository.findById(id)
                .orElseThrow(() -> new CosmoCatNotFoundException(id));


        existingEntity.setName(cosmoCatDto.getName() != null ? cosmoCatDto.getName() : existingEntity.getName());
        existingEntity.setEmail(cosmoCatDto.getEmail() != null ? cosmoCatDto.getEmail() : existingEntity.getEmail());
        existingEntity.setPhoneNumber(cosmoCatDto.getPhoneNumber() != null ? cosmoCatDto.getPhoneNumber() : existingEntity.getPhoneNumber());
        existingEntity.setAddress(cosmoCatDto.getAddress() != null ? cosmoCatDto.getAddress() : existingEntity.getAddress());

        CustomerEntity updatedEntity = customerRepository.save(existingEntity);
        return cosmoCatMapper.entityToDto(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteCosmoCat(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new CosmoCatNotFoundException(id);
        }
        customerRepository.deleteById(id);
    }
}