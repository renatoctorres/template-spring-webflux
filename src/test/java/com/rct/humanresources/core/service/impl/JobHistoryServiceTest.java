package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import com.rct.humanresources.core.model.mapper.JobHistoryMapper;
import com.rct.humanresources.core.model.stub.JobHistoryDTOStub;
import com.rct.humanresources.infra.persistence.entity.stub.JobHistoryStub;
import com.rct.humanresources.infra.persistence.model.JobHistory;
import com.rct.humanresources.infra.persistence.repository.JobHistoryRepository;
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
class JobHistoryServiceTest {
    @InjectMocks
    private JobHistoryServiceImpl service;
    @Mock
    private JobHistoryMapper mapper;
    @Mock
    private JobHistoryRepository repository;
    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;
    JobHistory entity = JobHistoryStub.any();
    JobHistoryDTO dto = JobHistoryDTOStub.any();

    @Test
    void shouldCreateJobHistory() {
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<JobHistoryDTO> monoEntity = service.create(dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindAllJobHistories() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findAll()).thenReturn(Flux.just(entity));
        Flux<JobHistoryDTO> fluxEntity = service.findAll();
        StepVerifier.create(fluxEntity).consumeNextWith(newJobHistory -> assertEquals(newJobHistory.getId(), dto.getId())).verifyComplete();
    }

    @Test
    void shouldFindJobHistoryById() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findById(dto.getId())).thenReturn(Mono.just(entity));
        Mono<JobHistoryDTO> monoEntity = service.findById(dto.getId());
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindJobHistoryByJobId() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findByJobId(dto.getJobId())).thenReturn(Flux.just(entity));
        Flux<JobHistoryDTO> fluxEntity = service.findByJobId(dto.getJobId());
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindJobHistoryByDepartmentId() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findByDepartmentId(dto.getDepartmentId())).thenReturn(Flux.just(entity));
        Flux<JobHistoryDTO> fluxEntity = service.findByDepartmentId(dto.getDepartmentId());
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldUpdateJobHistoryById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<JobHistoryDTO> monoEntity = service.updateById(dto.getId(), dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

}
