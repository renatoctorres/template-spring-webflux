package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.mapper.DepartmentMapper;
import com.rct.humanresources.core.model.dto.DepartmentDTO;
import com.rct.humanresources.core.service.DepartmentService;
import com.rct.humanresources.infra.persistence.model.Department;
import com.rct.humanresources.infra.persistence.repository.DepartmentRepository;
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
 * Department Service - Implementation
 */
@Service
@Slf4j
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    ReactiveMongoTemplate reactiveMongoTemplate;
    DepartmentMapper mapper;
    DepartmentRepository repository;
    EmployerRepository employerRepository;

    /**
     * Department Service Implementation - Constructor
     * @param reactiveMongoTemplate ReactiveMongoTemplate
     * @param departmentRepository DepartmentRepository
     * @param employerRepository EmployerRepository
     */
    @Autowired
    public DepartmentServiceImpl(ReactiveMongoTemplate reactiveMongoTemplate, DepartmentRepository departmentRepository,
                                 EmployerRepository employerRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.repository = departmentRepository;
        this.employerRepository = employerRepository;
    }

    /**
     * Create DepartmentDTO
     * @param dto DepartmentDTO
     * @return Mono DepartmentDTO
     */
    public Mono<DepartmentDTO> create(DepartmentDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Find All Departments
     * @return Flux DepartmentDTO
     */
    public Flux<DepartmentDTO> findAll(){
        return repository
                .findAll()
                .map(mapper::fromModel);
    }

    /**
     * Find All Department by Manager ID
     * @param managerId Long
     * @return Flux DepartmentDTO
     */
    public Flux<DepartmentDTO> findByManagerId(Long managerId){
        return repository
                .findByManagerId(managerId)
                .map(mapper::fromModel);
    }

    /**
     * Find All Department by Location ID
     * @param locationId Long
     * @return Flux DepartmentDTO
     */
    public Flux<DepartmentDTO> findByLocationId(Long locationId){
        return repository
                .findByLocationId(locationId)
                .map(mapper::fromModel);
    }

    /**
     * Find Department by ID
     * @param id Long
     * @return Mono Department
     */
    public Mono<DepartmentDTO> findById(Long id){
        return repository.findById(id).map(mapper::fromModel);
    }

    /**
     * Update Department
     * @param id Long
     * @param dto DepartmentDTO
     * @return Mono DepartmentDTO
     */
    public Mono<DepartmentDTO> update(Long id, DepartmentDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Delete Department
     * @param id Long
     * @return Mono DepartmentDTO
     */
    public Mono<DepartmentDTO> deleteById(Long id){
        return repository.findById(id)
                .flatMap(item -> repository.delete(item).then(Mono.just(item)))
                .map(mapper::fromModel);
    }

    /**
     * Fetch Department by Name
     * @param name String
     * @return Flux DepartmentDTO
     */
    public Flux<DepartmentDTO> fetchByName(String name) {
        var query = new Query().with(Sort.by(singletonList(asc("name"))));

        query.addCriteria(Criteria
                .where("name")
                .regex(name)
        );
        return reactiveMongoTemplate
                .find(query, Department.class)
                .map(mapper::fromModel);
    }

}
