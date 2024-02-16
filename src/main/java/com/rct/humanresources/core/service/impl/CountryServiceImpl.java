package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.mapper.CountryMapper;
import com.rct.humanresources.core.model.dto.CountryDTO;
import com.rct.humanresources.core.service.CountryService;
import com.rct.humanresources.infra.persistence.model.Country;
import com.rct.humanresources.infra.persistence.repository.CountryRepository;
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
 * Country Service Implementation
 */
@Service
@Slf4j
@Transactional
public class CountryServiceImpl implements CountryService {
    ReactiveMongoTemplate reactiveMongoTemplate;
    CountryMapper mapper;
    CountryRepository repository;

    /**
     * Country Service Implementation - Constructor
     * @param reactiveMongoTemplate ReactiveMongoTemplate
     * @param repository CountryRepository
     */
    @Autowired
    public CountryServiceImpl(ReactiveMongoTemplate reactiveMongoTemplate, CountryMapper mapper,
                              CountryRepository repository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.mapper = mapper;
        this.repository = repository;
    }

    /**
     * Create Country
     * @param dto CountryDTO
     * @return Mono CountryDTO
     */
    public Mono<CountryDTO> create(CountryDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Find All Countries
     * @return Flux CountryDTO
     */
    public Flux<CountryDTO> findAll(){
        return repository
                .findAll()
                .map(mapper::fromModel);
    }

    /**
     * Find Country by ID
     * @param id Long
     * @return Mono CountryDTO
     */
    public Mono<CountryDTO> findById(Long id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Update Country by ID
     * @param id Long
     * @param dto CountryDTO
     * @return Mono CountryDTO
     */
    public Mono<CountryDTO> update(Long id, CountryDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Delete Country by ID
     * @param id Long
     * @return Mono CountryDTO
     */
    public Mono<CountryDTO> deleteById(Long id){
        return repository
                .findById(id)
                .flatMap(item -> repository.delete(item).then(Mono.just(item)))
                .map(mapper::fromModel);
    }

    /**
     * Fetch Countries by Name
     * @param name String
     * @return Flux CountryDTO
     */
    public Flux<CountryDTO> fetchByName(String name) {
        var query = new Query().with(Sort.by(singletonList(asc("name"))));

        query.addCriteria(Criteria
                .where("name")
                .regex(name)
        );
        return reactiveMongoTemplate
                .find(query, Country.class)
                .map(mapper::fromModel);
    }

}
