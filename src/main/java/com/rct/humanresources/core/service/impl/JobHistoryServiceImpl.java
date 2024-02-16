package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.mapper.JobHistoryMapper;
import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import com.rct.humanresources.core.service.JobHistoryService;
import com.rct.humanresources.infra.persistence.model.JobHistory;
import com.rct.humanresources.infra.persistence.repository.JobHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Collections.singletonList;
import static org.springframework.data.domain.Sort.Order.asc;

/**
 * JobHistory Service Implementation
 */
@Service
@Slf4j
@Transactional
public class JobHistoryServiceImpl implements JobHistoryService {
    ReactiveMongoTemplate reactiveMongoTemplate;
    JobHistoryMapper mapper;
    JobHistoryRepository repository;

    /**
     * JobHistory Service Implementation - Constructor
     * @param reactiveMongoTemplate ReactiveMongoTemplate
     * @param repository JobHistoryRepository
     */
    @Autowired
    public JobHistoryServiceImpl(ReactiveMongoTemplate reactiveMongoTemplate, JobHistoryMapper mapper,
                                 JobHistoryRepository repository) {
        this.mapper = mapper;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.repository = repository;
    }

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
     * @param departmentId Long
     * @return Flux JobHistoryDTO
     */
    public Flux<JobHistoryDTO> findByDepartmentId(Long departmentId){
        return repository
                .findByDepartmentId(departmentId)
                .map(mapper::fromModel);
    }

    /**
     * Find All JobHistories by job ID
     * @param jobId Long
     * @return Flux JobHistoryDTO
     */
    public Flux<JobHistoryDTO> findByJobId(Long jobId){
        return repository
                .findByJobId(jobId)
                .map(mapper::fromModel);
    }

    /**
     * Find JobHistory by ID
     * @param id Long
     * @return Mono JobHistoryDTO
     */
    public Mono<JobHistoryDTO> findById(Long id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Update JobHistory by ID
     * @param id Long
     * @param dto JobHistoryDTO
     * @return Mono JobHistoryDTO
     */
    public Mono<JobHistoryDTO> update(Long id, JobHistoryDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Delete JobHistory by ID
     * @param id Long
     * @return Mono JobHistoryDTO
     */
    public Mono<JobHistoryDTO> deleteById(Long id){
        return repository.findById(id)
                .flatMap(item -> repository.delete(item).then(Mono.just(item)))
                .map(mapper::fromModel);
    }

    /**
     * Fetch JobHistories by Name
     * @param name String
     * @return Flux JobHistoryDTO
     */
    public Flux<JobHistoryDTO> fetchByName(String name) {
        var query = new Query().with(Sort.by(singletonList(asc("name"))));

        query.addCriteria(Criteria
                .where("name")
                .regex(name)
        );
        return reactiveMongoTemplate
                .find(query, JobHistory.class)
                .map(mapper::fromModel);
    }

}
