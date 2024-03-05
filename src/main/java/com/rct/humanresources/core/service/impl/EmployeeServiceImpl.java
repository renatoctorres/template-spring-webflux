package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.EmployeeDTO;
import com.rct.humanresources.core.model.mapper.EmployeeMapper;
import com.rct.humanresources.core.service.EmployeeService;
import com.rct.humanresources.infra.persistence.model.Employee;
import com.rct.humanresources.infra.persistence.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import static reactor.core.publisher.Mono.empty;

/**
 * Employee Service Implementation
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final EmployeeMapper mapper;
    private final EmployeeRepository repository;

    /**
     * Create Employee
     * @param dto EmployeeDTO
     * @return Mono EmployeeDTO
     */
    public Mono<EmployeeDTO> create(EmployeeDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Find All Employees
     * @return Flux EmployeeDTO
     */
    public Flux<EmployeeDTO> findAll(){
        return repository
                .findAll()
                .map(mapper::fromModel);
    }

    /**
     * Find Employee by ID
     * @param id String
     * @return Mono EmployeeDTO
     */
    public Mono<EmployeeDTO> findById(String id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Find All Employees by Department ID
     * @param departmentId String
     * @return Flux EmployeeDTO
     */
    public Flux<EmployeeDTO> findByDepartmentId(String departmentId){
        return repository
                .findByDepartmentId(departmentId)
                .map(mapper::fromModel);
    }

    /**
     * Find All Employees by Department ID
     * @param managerId String
     * @return Flux EmployeeDTO
     */
    public Flux<EmployeeDTO> findByManagerId(String managerId){
        return repository
                .findByManagerId(managerId)
                .map(mapper::fromModel);
    }

    /**
     * Find All Employees by Job ID
     * @param jobId String
     * @return Flux EmployeeDTO
     */
    public Flux<EmployeeDTO> findByJobId(String jobId){
        return repository
                .findByJobId(jobId)
                .map(mapper::fromModel);
    }

    /**
     * Update Employee by ID
     * @param id String
     * @param dto EmployeeDTO
     * @return Mono EmployeeDTO
     */
    public Mono<EmployeeDTO> updateById(String id, EmployeeDTO dto){
        return repository
                .findById(id)
                .flatMap(item ->
                        repository.save(mapper.fromDTO(dto))
                )
                .switchIfEmpty(empty())
                .map(mapper::fromModel);
    }

    /**
     * Delete Employee by ID
     * @param id String
     * @return Mono EmployeeDTO
     */
    public Mono<EmployeeDTO> deleteById(String id){
        return repository.findById(id)
                .flatMap(item -> repository.delete(item).then(Mono.just(item))).map(mapper::fromModel);
    }

    /**
     * Fetch Employees by Name
     * @param name String
     * @return Flux EmployeeDTO
     */
    public Flux<EmployeeDTO> fetchByName(String name) {
        var query = new Query().with(Sort.by(singletonList(asc("name"))));

        query.addCriteria(Criteria
                .where("name")
                .regex(name)
        );
        return reactiveMongoTemplate
                .find(query, Employee.class)
                .map(mapper::fromModel);
    }

}
