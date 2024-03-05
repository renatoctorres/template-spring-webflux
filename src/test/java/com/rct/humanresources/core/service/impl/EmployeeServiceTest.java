package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.EmployeeDTO;
import com.rct.humanresources.core.model.mapper.EmployeeMapper;
import com.rct.humanresources.core.model.stub.EmployeeDTOStub;
import com.rct.humanresources.infra.persistence.entity.stub.EmployeeStub;
import com.rct.humanresources.infra.persistence.model.Employee;
import com.rct.humanresources.infra.persistence.repository.EmployeeRepository;
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
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeServiceImpl service;
    @Mock
    private EmployeeMapper mapper;
    @Mock
    private EmployeeRepository repository;
    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;
    Employee entity = EmployeeStub.any();
    EmployeeDTO dto = EmployeeDTOStub.any();

    @Test
    void shouldCreateEmployee() {
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<EmployeeDTO> monoEntity = service.create(dto);
        StepVerifier
                .create(monoEntity)
                .consumeNextWith(item -> assertEquals(item, dto))
                .verifyComplete();
    }

    @Test
    void shouldFindAllEmployees() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findAll()).thenReturn(Flux.just(entity));
        Flux<EmployeeDTO> fluxEntity = service.findAll();
        StepVerifier
                .create(fluxEntity)
                .consumeNextWith(item ->  assertEquals(item, dto))
                .verifyComplete();
    }

    @Test
    void shouldFindEmployeeById() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository
                .findById(dto.getId()))
                .thenReturn(Mono.just(entity));
        Mono<EmployeeDTO> monoEntity = service.findById(dto.getId());
        StepVerifier
                .create(monoEntity)
                .consumeNextWith(item -> assertEquals(item, dto))
                .verifyComplete();
    }

    @Test
    void shouldFindEmployeeByDepartmentId() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository
                .findByDepartmentId(dto.getJobId()))
                .thenReturn(Flux.just(entity));
        Flux<EmployeeDTO> fluxEntity = service.findByDepartmentId(dto.getJobId());
        StepVerifier
                .create(fluxEntity)
                .consumeNextWith(item -> assertEquals(item, dto))
                .verifyComplete();
    }

    @Test
    void shouldFindEmployeeByJobId() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository
                .findByJobId(dto.getJobId()))
                .thenReturn(Flux.just(entity));
        Flux<EmployeeDTO> fluxEntity = service.findByJobId(dto.getJobId());
        StepVerifier
                .create(fluxEntity)
                .consumeNextWith(item -> assertEquals(item, dto))
                .verifyComplete();
    }

    @Test
    void shouldFindEmployeeByManagerId() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository
                .findByManagerId(dto.getManagerId()))
                .thenReturn(Flux.just(entity));
        Flux<EmployeeDTO> fluxEntity = service.findByManagerId(dto.getManagerId());
        StepVerifier
                .create(fluxEntity)
                .consumeNextWith(item -> assertEquals(item, dto))
                .verifyComplete();
    }

    @Test
    void shouldUpdateEmployee() {
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<EmployeeDTO> monoEntity = service.updateById(dto.getId(),dto);
        StepVerifier
                .create(monoEntity)
                .consumeNextWith(item -> assertEquals(item, dto))
                .verifyComplete();
    }

    @Test
    void shouldFetchEmployeeByName() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(reactiveMongoTemplate.find(any(),eq(Employee.class))).thenReturn(Flux.just(entity));
        Flux<EmployeeDTO> fluxEntity = service.fetchByName("name");
        StepVerifier
                .create(fluxEntity)
                .consumeNextWith(item -> assertEquals(item, dto))
                .verifyComplete();
    }
}
