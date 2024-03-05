package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.CityDTO;
import com.rct.humanresources.core.model.mapper.CityMapper;
import com.rct.humanresources.core.service.CityService;
import com.rct.humanresources.infra.persistence.model.City;
import com.rct.humanresources.infra.persistence.repository.CityRepository;
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
 * City Service Implementation
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityMapper mapper;
    private final CityRepository repository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    
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
     * @param id String
     * @return Mono CityDTO
     */
    public Mono<CityDTO> findById(String id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Find All Cities by Department ID
     * @param stateId String
     * @return Flux CityDTO
     */
    public Flux<CityDTO> findByStateId(String stateId){
        return repository
                .findByStateId(stateId)
                .map(mapper::fromModel);
    }


    /**
     * Update City by ID
     * @param id String
     * @param dto CityDTO
     * @return Mono CityDTO
     */
    public Mono<CityDTO> updateById(String id, CityDTO dto){
        return repository
                .findById(id)
                .flatMap(item ->
                    repository.save(mapper.fromDTO(dto))
                )
                .switchIfEmpty(empty())
                .map(mapper::fromModel);
    }

    /**
     * Delete City by ID
     * @param id String
     * @return Mono CityDTO
     */
    public Mono<CityDTO> deleteById(String id){
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
