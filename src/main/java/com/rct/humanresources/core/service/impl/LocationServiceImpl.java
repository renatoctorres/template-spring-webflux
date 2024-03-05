package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.LocationDTO;
import com.rct.humanresources.core.model.mapper.LocationMapper;
import com.rct.humanresources.core.service.LocationService;
import com.rct.humanresources.infra.persistence.model.Location;
import com.rct.humanresources.infra.persistence.repository.LocationRepository;
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
 * Location Service Implementation
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationMapper mapper;
    private final LocationRepository repository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    /**
     * Create Location
     * @param dto LocationDTO
     * @return Mono LocationDTO
     */
    public Mono<LocationDTO> create(LocationDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Find All Locations
     * @return Flux LocationDTO
     */
    public Flux<LocationDTO> findAll(){
        return repository
                .findAll()
                .map(mapper::fromModel);
    }

    /**
     * Find Location by ID
     * @param id String
     * @return Mono LocationDTO
     */
    public Mono<LocationDTO> findById(String id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Find All Locations by City ID
     * @param cityId String
     * @return Flux LocationDTO
     */
    public Flux<LocationDTO> findByCityId(String cityId){
        return repository
                .findByCityId(cityId)
                .map(mapper::fromModel);
    }


    /**
     * Update Location by ID
     * @param id String
     * @param dto LocationDTO
     * @return Mono LocationDTO
     */
    public Mono<LocationDTO> updateById(String id, LocationDTO dto){
        return repository
                .findById(id)
                .flatMap(item ->
                        repository.save(mapper.fromDTO(dto))
                )
                .switchIfEmpty(empty())
                .map(mapper::fromModel);
    }

    /**
     * Delete Location by ID
     * @param id String
     * @return Mono LocationDTO
     */
    public Mono<LocationDTO> deleteById(String id){
        return repository
                .findById(id)
                .flatMap(item -> repository.delete(item).then(Mono.just(item)))
                .map(mapper::fromModel);
    }

    /**
     * Fetch Locations by Name
     * @param name String
     * @return Flux LocationDTO
     */
    public Flux<LocationDTO> fetchByName(String name) {
        var query = new Query().with(Sort.by(singletonList(asc("name"))));

        query.addCriteria(Criteria
                .where("name")
                .regex(name)
        );
        return reactiveMongoTemplate
                .find(query, Location.class)
                .map(mapper::fromModel);
    }

}
