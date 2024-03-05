package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.DepartmentDTO;
import com.rct.humanresources.core.model.mapper.DepartmentMapper;
import com.rct.humanresources.core.model.stub.DepartmentDTOStub;
import com.rct.humanresources.infra.persistence.entity.stub.DepartmentStub;
import com.rct.humanresources.infra.persistence.model.Department;
import com.rct.humanresources.infra.persistence.repository.DepartmentRepository;
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
class DepartmentServiceTest {
    @InjectMocks
    private DepartmentServiceImpl service;
    @Mock
    private DepartmentMapper mapper;
    @Mock
    private DepartmentRepository repository;
    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;
    Department entity = DepartmentStub.any();
    DepartmentDTO dto = DepartmentDTOStub.any();

    @Test
    void shouldCreateDepartment() {
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<DepartmentDTO> monoEntity = service.create(dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> {
            assertEquals(item, dto);
        }).verifyComplete();
    }

    @Test
    void shouldFindAllDepartments() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findAll()).thenReturn(Flux.just(entity));
        Flux<DepartmentDTO> fluxEntity = service.findAll();
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindDepartmentById() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findById(dto.getId())).thenReturn(Mono.just(entity));
        Mono<DepartmentDTO> monoEntity = service.findById(dto.getId());
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindDepartmentByManagerId() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findByManagerId(dto.getManagerId())).thenReturn(Flux.just(entity));
        Flux<DepartmentDTO> fluxEntity = service.findByManagerId(dto.getManagerId());
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindDepartmentByLocationId() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findByLocationId(dto.getLocationId())).thenReturn(Flux.just(entity));
        Flux<DepartmentDTO> fluxEntity = service.findByLocationId(dto.getLocationId());
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldUpdateDepartment() {
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<DepartmentDTO> monoEntity = service.updateById(dto.getId(), dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFetchDepartmentByName() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(reactiveMongoTemplate.find(any(), eq(Department.class))).thenReturn(Flux.just(entity));
        Flux<DepartmentDTO> fluxEntity = service.fetchByName("name");
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }
}
