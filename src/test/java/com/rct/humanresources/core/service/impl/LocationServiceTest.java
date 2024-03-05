package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.LocationDTO;
import com.rct.humanresources.core.model.mapper.LocationMapper;
import com.rct.humanresources.core.model.stub.LocationDTOStub;
import com.rct.humanresources.infra.persistence.entity.stub.LocationStub;
import com.rct.humanresources.infra.persistence.model.Location;
import com.rct.humanresources.infra.persistence.repository.LocationRepository;
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
class LocationServiceTest {
    @InjectMocks
    private LocationServiceImpl service;
    @Mock
    private LocationMapper mapper;
    @Mock
    private LocationRepository repository;
    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;
    Location entity = LocationStub.any();
    LocationDTO dto = LocationDTOStub.any();

    @Test
    void shouldCreateLocation() {
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<LocationDTO> monoEntity = service.create(dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindAllLocations() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findAll()).thenReturn(Flux.just(entity));
        Flux<LocationDTO> fluxEntity = service.findAll();
        StepVerifier.create(fluxEntity).consumeNextWith(item ->  assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindLocationById() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findById(dto.getId())).thenReturn(Mono.just(entity));
        Mono<LocationDTO> monoEntity = service.findById(dto.getId());
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindLocationByCityId() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findByCityId(dto.getCityId())).thenReturn(Flux.just(entity));
        Flux<LocationDTO> fluxEntity = service.findByCityId(dto.getCityId());
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldUpdateLocationById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<LocationDTO> monoEntity = service.updateById(dto.getId(), dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFetchLocationByName() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(reactiveMongoTemplate.find(any(), eq(Location.class))).thenReturn(Flux.just(entity));
        Flux<LocationDTO> fluxEntity = service.fetchByName("name");
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

}
