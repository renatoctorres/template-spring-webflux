package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.mapper.StateMapper;
import com.rct.humanresources.core.model.dto.StateDTO;
import com.rct.humanresources.core.service.StateService;
import com.rct.humanresources.infra.persistence.model.State;
import com.rct.humanresources.infra.persistence.repository.StateRepository;
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
 * State Service Implementation
 */
@Service
@Slf4j
@Transactional
public class StateServiceImpl implements StateService {
    ReactiveMongoTemplate reactiveMongoTemplate;
    StateMapper mapper;
    StateRepository repository;

    /**
     * State Service Implementation - Constructor
     * @param reactiveMongoTemplate ReactiveMongoTemplate
     * @param repository StateRepository
     */
    @Autowired
    public StateServiceImpl(ReactiveMongoTemplate reactiveMongoTemplate, StateMapper mapper,
                            StateRepository repository) {
        this.mapper = mapper;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.repository = repository;
    }

    /**
     * Create State
     * @param dto StateDTO
     * @return Mono StateDTO
     */
    public Mono<StateDTO> create(StateDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Find All States
     * @return Flux StateDTO
     */
    public Flux<StateDTO> findAll(){
        return repository
                .findAll()
                .map(mapper::fromModel);
    }

    /**
     * Find State by ID
     * @param id Long
     * @return Mono StateDTO
     */
    public Mono<StateDTO> findById(Long id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Find All States by Department ID
     * @param countryId Long
     * @return Flux StateDTO
     */
    public Flux<StateDTO> findByCountryId(Long countryId){
        return repository
                .findByCountryId(countryId)
                .map(mapper::fromModel);
    }


    /**
     * Update State by ID
     * @param id Long
     * @param dto StateDTO
     * @return Mono StateDTO
     */
    public Mono<StateDTO> update(Long id, StateDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Delete State by ID
     * @param id Long
     * @return Mono StateDTO
     */
    public Mono<StateDTO> deleteById(Long id){
        return repository.findById(id)
                .flatMap(item -> repository.delete(item).then(Mono.just(item)))
                .map(mapper::fromModel);
    }

    /**
     * Fetch States by Name
     * @param name String
     * @return Flux StateDTO
     */
    public Flux<StateDTO> fetchByName(String name) {
        var query = new Query().with(Sort.by(singletonList(asc("name"))));

        query.addCriteria(Criteria
                .where("name")
                .regex(name)
        );
        return reactiveMongoTemplate.find(query, State.class)
                .map(mapper::fromModel);
    }

}
