package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.DepartmentDTO;
import com.rct.humanresources.core.model.mapper.DepartmentMapper;
import com.rct.humanresources.core.service.DepartmentService;
import com.rct.humanresources.infra.persistence.model.Department;
import com.rct.humanresources.infra.persistence.repository.DepartmentRepository;
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
 * Department Service - Implementation
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final DepartmentMapper mapper;
    private final DepartmentRepository repository;

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
     * @param managerId String
     * @return Flux DepartmentDTO
     */
    public Flux<DepartmentDTO> findByManagerId(String managerId){
        return repository
                .findByManagerId(managerId)
                .map(mapper::fromModel);
    }

    /**
     * Find All Department by Location ID
     * @param locationId String
     * @return Flux DepartmentDTO
     */
    public Flux<DepartmentDTO> findByLocationId(String locationId){
        return repository
                .findByLocationId(locationId)
                .map(mapper::fromModel);
    }

    /**
     * Find Department by ID
     * @param id String
     * @return Mono Department
     */
    public Mono<DepartmentDTO> findById(String id){
        return repository.findById(id).map(mapper::fromModel);
    }

    /**
     * Update Department
     * @param id String
     * @param dto DepartmentDTO
     * @return Mono DepartmentDTO
     */
    public Mono<DepartmentDTO> updateById(String id, DepartmentDTO dto){
        return repository
                .findById(id)
                .flatMap(item ->
                        repository.save(mapper.fromDTO(dto))
                )
                .switchIfEmpty(empty())
                .map(mapper::fromModel);
    }

    /**
     * Delete Department
     * @param id String
     * @return Mono DepartmentDTO
     */
    public Mono<DepartmentDTO> deleteById(String id){
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
