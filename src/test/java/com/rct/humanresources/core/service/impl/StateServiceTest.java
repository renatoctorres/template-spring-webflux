package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.StateDTO;
import com.rct.humanresources.core.model.mapper.StateMapper;
import com.rct.humanresources.core.model.stub.StateDTOStub;
import com.rct.humanresources.infra.persistence.entity.stub.StateStub;
import com.rct.humanresources.infra.persistence.model.State;
import com.rct.humanresources.infra.persistence.repository.StateRepository;
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
class StateServiceTest {
    @InjectMocks
    private StateServiceImpl service;
    @Mock
    private StateMapper mapper;
    @Mock
    private StateRepository repository;
    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;
    State entity = StateStub.any();
    StateDTO dto = StateDTOStub.any();

    @Test
    void shouldCreateState() {
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<StateDTO> monoEntity = service.create(dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> {
            assertEquals(item, dto);
        }).verifyComplete();
    }

    @Test
    void shouldFindAllStates() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findAll()).thenReturn(Flux.just(entity));
        Flux<StateDTO> fluxEntity = service.findAll();
        StepVerifier.create(fluxEntity).consumeNextWith(newState -> {
            assertEquals(newState.getId(), dto.getId());
        }).verifyComplete();
    }

    @Test
    void shouldFindStateById() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findById(dto.getId())).thenReturn(Mono.just(entity));
        Mono<StateDTO> monoEntity = service.findById(dto.getId());
        StepVerifier.create(monoEntity).consumeNextWith(item -> {
            assertEquals(item, dto);
        }).verifyComplete();
    }

    @Test
    void shouldFindStateByCountryId() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findByCountryId(dto.getCountryId())).thenReturn(Flux.just(entity));
        Flux<StateDTO> fluxEntity = service.findByCountryId(dto.getCountryId());
        StepVerifier.create(fluxEntity).consumeNextWith(item -> {
            assertEquals(item, dto);
        }).verifyComplete();
    }

    @Test
    void shouldUpdateStateByID() {
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<StateDTO> monoEntity = service.updateById(dto.getId(), dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> {
            assertEquals(item, dto);
        }).verifyComplete();
    }

    @Test
    void shouldFetchStateByName() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(reactiveMongoTemplate.find(any(), eq(State.class))).thenReturn(Flux.just(entity));
        Flux<StateDTO> fluxEntity = service.fetchByName("name");
        StepVerifier.create(fluxEntity).consumeNextWith(item ->  assertEquals(item, dto)).verifyComplete();
    }

}