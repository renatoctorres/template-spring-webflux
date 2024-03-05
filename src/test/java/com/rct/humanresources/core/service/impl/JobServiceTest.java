package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.JobDTO;
import com.rct.humanresources.core.model.mapper.JobMapper;
import com.rct.humanresources.core.model.stub.JobDTOStub;
import com.rct.humanresources.infra.persistence.entity.stub.JobStub;
import com.rct.humanresources.infra.persistence.model.Job;
import com.rct.humanresources.infra.persistence.repository.JobRepository;
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
class JobServiceTest {
    @InjectMocks
    private JobServiceImpl service;
    @Mock
    private JobMapper mapper;
    @Mock
    private JobRepository repository;
    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;
    Job entity = JobStub.any();
    JobDTO dto = JobDTOStub.any();

    @Test
    void shouldCreateJob() {
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<JobDTO> monoEntity = service.create(dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }


    @Test
    void shouldFindAllJobs() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findAll()).thenReturn(Flux.just(entity));
        Flux<JobDTO> fluxEntity = service.findAll();
        StepVerifier.create(fluxEntity).consumeNextWith(item ->  assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFindJobById() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(repository.findById(dto.getId())).thenReturn(Mono.just(entity));
        Mono<JobDTO> monoEntity = service.findById(dto.getId());
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldUpdateJobById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(mapper.fromDTO(dto)).thenReturn(entity);
        Mono<JobDTO> monoEntity = service.updateById(dto.getId(), dto);
        StepVerifier.create(monoEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

    @Test
    void shouldFetchJobByName() {
        when(mapper.fromModel(entity)).thenReturn(dto);
        when(reactiveMongoTemplate.find(any(), eq(Job.class))).thenReturn(Flux.just(entity));
        Flux<JobDTO> fluxEntity = service.fetchByName("name");
        StepVerifier.create(fluxEntity).consumeNextWith(item -> assertEquals(item, dto)).verifyComplete();
    }

}
