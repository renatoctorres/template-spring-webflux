package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import com.rct.humanresources.core.model.mapper.JobHistoryMapper;
import com.rct.humanresources.core.service.JobHistoryService;
import com.rct.humanresources.infra.persistence.repository.JobHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.empty;

/**
 * JobHistory Service Implementation
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class JobHistoryServiceImpl implements JobHistoryService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final JobHistoryMapper mapper;
    private final JobHistoryRepository repository;

    /**
     * Create JobHistory
     * @param dto JobHistoryDTO
     * @return Mono JobHistoryDTO
     */
    public Mono<JobHistoryDTO> create(JobHistoryDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Find All JobHistories
     * @return Flux JobHistoryDTO
     */
    public Flux<JobHistoryDTO> findAll(){
        return repository
                .findAll()
                .map(mapper::fromModel);
    }

    /**
     * Find All JobHistories by Department ID
     * @param departmentId String
     * @return Flux JobHistoryDTO
     */
    public Flux<JobHistoryDTO> findByDepartmentId(String departmentId){
        return repository
                .findByDepartmentId(departmentId)
                .map(mapper::fromModel);
    }

    /**
     * Find All JobHistories by job ID
     * @param jobId String
     * @return Flux JobHistoryDTO
     */
    public Flux<JobHistoryDTO> findByJobId(String jobId){
        return repository
                .findByJobId(jobId)
                .map(mapper::fromModel);
    }

    /**
     * Find JobHistory by ID
     * @param id String
     * @return Mono JobHistoryDTO
     */
    public Mono<JobHistoryDTO> findById(String id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Update JobHistory by ID
     * @param id String
     * @param dto JobHistoryDTO
     * @return Mono JobHistoryDTO
     */
    public Mono<JobHistoryDTO> updateById(String id, JobHistoryDTO dto){
        return repository
                .findById(id)
                .flatMap(item ->
                        repository.save(mapper.fromDTO(dto))
                )
                .switchIfEmpty(empty())
                .map(mapper::fromModel);
    }

    /**
     * Delete JobHistory by ID
     * @param id String
     * @return Mono JobHistoryDTO
     */
    public Mono<JobHistoryDTO> deleteById(String id){
        return repository.findById(id)
                .flatMap(item -> repository.delete(item).then(Mono.just(item)))
                .map(mapper::fromModel);
    }


}
