package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.StateDTO;
import com.rct.humanresources.core.model.mapper.StateMapper;
import com.rct.humanresources.core.service.StateService;
import com.rct.humanresources.infra.persistence.model.State;
import com.rct.humanresources.infra.persistence.repository.StateRepository;
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
 * State Service Implementation
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final StateMapper mapper;
    private final StateRepository repository;

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
     * @param id String
     * @return Mono StateDTO
     */
    public Mono<StateDTO> findById(String id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Find All States by Department ID
     * @param countryId String
     * @return Flux StateDTO
     */
    public Flux<StateDTO> findByCountryId(String countryId){
        return repository
                .findByCountryId(countryId)
                .map(mapper::fromModel);
    }


    /**
     * Update State by ID
     * @param id String
     * @param dto StateDTO
     * @return Mono StateDTO
     */
    public Mono<StateDTO> updateById(String id, StateDTO dto){
        return repository
                .findById(id)
                .flatMap(item ->
                        repository.save(mapper.fromDTO(dto))
                )
                .switchIfEmpty(empty())
                .map(mapper::fromModel);
    }

    /**
     * Delete State by ID
     * @param id String
     * @return Mono StateDTO
     */
    public Mono<StateDTO> deleteById(String id){
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
