package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.CountryDTO;
import com.rct.humanresources.core.model.mapper.CountryMapper;
import com.rct.humanresources.core.service.CountryService;
import com.rct.humanresources.infra.persistence.model.Country;
import com.rct.humanresources.infra.persistence.repository.CountryRepository;
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
 * Country Service Implementation
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final CountryMapper mapper;
    private final CountryRepository repository;

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
     * @param id String
     * @return Mono CountryDTO
     */
    public Mono<CountryDTO> findById(String id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Update Country by ID
     * @param id String
     * @param dto CountryDTO
     * @return Mono CountryDTO
     */
    public Mono<CountryDTO> updateById(String id, CountryDTO dto){
        return repository
                .findById(id)
                .flatMap(item ->
                        repository.save(mapper.fromDTO(dto))
                )
                .switchIfEmpty(empty())
                .map(mapper::fromModel);
    }

    /**
     * Delete Country by ID
     * @param id String
     * @return Mono CountryDTO
     */
    public Mono<CountryDTO> deleteById(String id){
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
