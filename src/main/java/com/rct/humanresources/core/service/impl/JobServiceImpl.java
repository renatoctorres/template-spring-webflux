package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.mapper.JobMapper;
import com.rct.humanresources.core.model.dto.JobDTO;
import com.rct.humanresources.core.service.JobService;
import com.rct.humanresources.infra.persistence.model.Job;
import com.rct.humanresources.infra.persistence.repository.JobRepository;
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
 * Job Service Implementation
 */
@Service
@Slf4j
@Transactional
public class JobServiceImpl implements JobService {
    JobMapper mapper;
    JobRepository repository;
    ReactiveMongoTemplate reactiveMongoTemplate;
    
    /**
     * Job Service Implementation - Constructor
     * @param reactiveMongoTemplate ReactiveMongoTemplate
     * @param mapper JobMapper
     * @param repository JobRepository
     */
    @Autowired
    public JobServiceImpl(JobMapper mapper,
                          JobRepository repository,
                          ReactiveMongoTemplate reactiveMongoTemplate) {
        this.mapper = mapper;
        this.repository = repository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        
    }

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
     * @param id Long
     * @return Mono Job
     */
    public Mono<JobDTO> findById(Long id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Update Job by ID
     * @param id Long
     * @param dto Job
     * @return Mono Job
     */
    public Mono<JobDTO> update(Long id, JobDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Delete Job by ID
     * @param id Long
     * @return Mono Job
     */
    public Mono<JobDTO> deleteById(Long id){
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
