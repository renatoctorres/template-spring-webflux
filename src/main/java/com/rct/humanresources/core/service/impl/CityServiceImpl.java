package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.mapper.CityMapper;
import com.rct.humanresources.core.model.dto.CityDTO;
import com.rct.humanresources.core.service.CityService;
import com.rct.humanresources.infra.persistence.model.City;
import com.rct.humanresources.infra.persistence.repository.CityRepository;
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
 * City Service Implementation
 */
@Service
@Slf4j
@Transactional
public class CityServiceImpl implements CityService {
    ReactiveMongoTemplate reactiveMongoTemplate;
    CityRepository repository;
    CityMapper mapper;

    /**
     * City Service Implementation - Constructor
     * @param reactiveMongoTemplate ReactiveMongoTemplate
     * @param repository CityRepository
     */
    @Autowired
    public CityServiceImpl(ReactiveMongoTemplate reactiveMongoTemplate, CityRepository repository,
                           CityMapper mapper) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Create City
     * @param dto CityDTO
     * @return Mono CityDTO
     */
    public Mono<CityDTO> create(CityDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Find All Cities
     * @return Flux City
     */
    public Flux<CityDTO> findAll(){
        return repository
                .findAll()
                .map(mapper::fromModel);
    }

    /**
     * Find City by ID
     * @param id Long
     * @return Mono CityDTO
     */
    public Mono<CityDTO> findById(Long id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Find All Cities by Department ID
     * @param stateId Long
     * @return Flux CityDTO
     */
    public Flux<CityDTO> findByStateId(Long stateId){
        return repository
                .findByStateId(stateId)
                .map(mapper::fromModel);
    }


    /**
     * Update City by ID
     * @param id Long
     * @param dto CityDTO
     * @return Mono CityDTO
     */
    public Mono<CityDTO> update(Long id, CityDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Delete City by ID
     * @param id Long
     * @return Mono CityDTO
     */
    public Mono<CityDTO> deleteById(Long id){
        return repository
                .findById(id)
                .flatMap(item -> repository.delete(item).then(Mono.just(item)))
                .map(mapper::fromModel);
    }

    /**
     * Fetch Cities by Name
     * @param name String
     * @return Flux CityDTO
     */
    public Flux<CityDTO> fetchByName(String name) {
        var query = new Query().with(Sort.by(singletonList(asc("name"))));

        query.addCriteria(Criteria
                .where("name")
                .regex(name)
        );
        return reactiveMongoTemplate
                .find(query, City.class)
                .map(mapper::fromModel);
    }

}
