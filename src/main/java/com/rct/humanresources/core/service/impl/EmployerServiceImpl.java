package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.mapper.EmployerMapper;
import com.rct.humanresources.core.model.dto.EmployerDTO;
import com.rct.humanresources.core.service.EmployerService;
import com.rct.humanresources.infra.persistence.model.Employer;
import com.rct.humanresources.infra.persistence.repository.EmployerRepository;
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
 * Employer Service Implementation
 */
@Service
@Slf4j
@Transactional
public class EmployerServiceImpl implements EmployerService {
    ReactiveMongoTemplate reactiveMongoTemplate;
    EmployerMapper mapper;
    EmployerRepository repository;
    
    /**
     * Employer Service Implementation - Constructor
     * @param reactiveMongoTemplate ReactiveMongoTemplate
     * @param repository EmployerRepository
     */
    @Autowired
    public EmployerServiceImpl(ReactiveMongoTemplate reactiveMongoTemplate,  EmployerMapper mapper,
                               EmployerRepository repository) {
        this.mapper = mapper;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.repository = repository;
    }

    /**
     * Create Employer
     * @param dto EmployerDTO
     * @return Mono EmployerDTO
     */
    public Mono<EmployerDTO> create(EmployerDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Find All Employers
     * @return Flux EmployerDTO
     */
    public Flux<EmployerDTO> findAll(){
        return repository
                .findAll()
                .map(mapper::fromModel);
    }

    /**
     * Find Employer by ID
     * @param id Long
     * @return Mono EmployerDTO
     */
    public Mono<EmployerDTO> findById(Long id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Find All Employers by Department ID
     * @param departmentId Long
     * @return Flux EmployerDTO
     */
    public Flux<EmployerDTO> findByDepartmentId(Long departmentId){
        return repository
                .findByDepartmentId(departmentId)
                .map(mapper::fromModel);
    }

    /**
     * Find All Employers by Department ID
     * @param managerId Long
     * @return Flux EmployerDTO
     */
    public Flux<EmployerDTO> findByManagerId(Long managerId){
        return repository
                .findByManagerId(managerId)
                .map(mapper::fromModel);
    }

    /**
     * Find All Employers by Job ID
     * @param jobId Long
     * @return Flux EmployerDTO
     */
    public Flux<EmployerDTO> findByJobId(Long jobId){
        return repository
                .findByJobId(jobId)
                .map(mapper::fromModel);
    }

    /**
     * Update Employer by ID
     * @param id Long
     * @param dto EmployerDTO
     * @return Mono EmployerDTO
     */
    public Mono<EmployerDTO> update(Long id, EmployerDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Delete Employer by ID
     * @param id Long
     * @return Mono EmployerDTO
     */
    public Mono<EmployerDTO> deleteById(Long id){
        return repository.findById(id)
                .flatMap(item -> repository.delete(item).then(Mono.just(item))).map(mapper::fromModel);
    }

    /**
     * Fetch Employees by Name
     * @param name String
     * @return Flux EmployerDTO
     */
    public Flux<EmployerDTO> fetchByName(String name) {
        var query = new Query().with(Sort.by(singletonList(asc("name"))));

        query.addCriteria(Criteria
                .where("name")
                .regex(name)
        );
        return reactiveMongoTemplate
                .find(query, Employer.class)
                .map(mapper::fromModel);
    }

}
