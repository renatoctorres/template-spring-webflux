package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.mapper.JobMapper;
import com.rct.humanresources.core.model.dto.JobDTO;
import com.rct.humanresources.core.service.JobService;
import com.rct.humanresources.infra.persistence.model.Job;
import com.rct.humanresources.infra.persistence.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Collections.singletonList;
import static org.springframework.data.domain.Sort.Order.asc;
import static reactor.core.publisher.Mono.empty;

/**
 * Job Service Implementation
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobMapper mapper;
    private final JobRepository repository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    /**
     * Create Job
     * @param dto Job
     * @return Mono Job
     */
    public Mono<JobDTO> create(JobDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
                
    }

    /**
     * Find All Jobs
     * @return Flux Job
     */
    public Flux<JobDTO> findAll(){
        return repository
                .findAll()
                .map(mapper::fromModel);
    }

    /**
     * Find Job by ID
     * @param id String
     * @return Mono Job
     */
    public Mono<JobDTO> findById(String id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Update Job by ID
     * @param id String
     * @param dto Job
     * @return Mono Job
     */
    public Mono<JobDTO> updateById(String id, JobDTO dto){
        return repository
                .findById(id)
                .flatMap(item ->
                        repository.save(mapper.fromDTO(dto))
                )
                .switchIfEmpty(empty())
                .map(mapper::fromModel);
    }

    /**
     * Delete Job by ID
     * @param id String
     * @return Mono Job
     */
    public Mono<JobDTO> deleteById(String id){
        return repository.findById(id)
                .flatMap(item -> repository.delete(item).then(Mono.just(item)))
                .map(mapper::fromModel);
    }

    /**
     * Fetch Jobs by Name
     * @param name String
     * @return Flux Job
     */
    public Flux<JobDTO> fetchByName(String name) {
        var query = new Query().with(Sort.by(singletonList(asc("name"))));

        query.addCriteria(Criteria
                .where("name")
                .regex(name)
        );
        return reactiveMongoTemplate
                .find(query, Job.class)
                .map(mapper::fromModel);
    }

}
