package com.rct.humanresources.core.service.impl;


import com.rct.humanresources.core.model.dto.CityDTO;
import com.rct.humanresources.core.model.mapper.CityMapper;
import com.rct.humanresources.core.model.stub.CityDTOStub;
import com.rct.humanresources.infra.persistence.entity.stub.CityStub;
import com.rct.humanresources.infra.persistence.model.City;
import com.rct.humanresources.infra.persistence.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {
    @InjectMocks
    private CityServiceImpl service;
    @Mock
    private CityMapper mapper;
    @Mock
    private CityRepository repository;
    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;
    City entity = CityStub.any();
    CityDTO dto = CityDTOStub.any();

    @Test
    void shouldCreateCity() {
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<CityDTO> monoEntity = service.create(dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindAllCities() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findAll()).thenReturn(Flux.just(entity));
        Flux<CityDTO> fluxEntity = service.findAll();
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindCityById() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findById(dto.getId())).thenReturn(Mono.just(entity));
        Mono<CityDTO> monoEntity = service.findById(dto.getId());
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindCityByStateId() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findByStateId(dto.getStateId())).thenReturn(Flux.just(entity));
        Flux<CityDTO> fluxEntity = service.findByStateId(dto.getStateId());
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldUpdateCity() {
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<CityDTO> monoEntity = service.updateById(dto.getId(), dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFetchCityByName() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(reactiveMongoTemplate.find(any(), eq(City.class))).thenReturn(Flux.just(entity));
        Flux<CityDTO> fluxEntity = service.fetchByName("name");
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

}
