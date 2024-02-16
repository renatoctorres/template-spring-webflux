package com.rct.humanresources.core.service.impl;

import com.rct.humanresources.core.model.dto.LocationDTO;
import com.rct.humanresources.core.mapper.LocationMapper;
import com.rct.humanresources.core.service.LocationService;
import com.rct.humanresources.infra.persistence.model.Location;
import com.rct.humanresources.infra.persistence.repository.LocationRepository;
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
 * Location Service Implementation
 */
@Service
@Slf4j
@Transactional
public class LocationServiceImpl implements LocationService {
    LocationMapper mapper;
    LocationRepository repository;
    ReactiveMongoTemplate reactiveMongoTemplate;
    
    /**
     * Location Service Implementation - Constructor
     * @param reactiveMongoTemplate ReactiveMongoTemplate
     * @param repository LocationRepository
     */
    @Autowired
    public LocationServiceImpl(LocationMapper mapper, LocationRepository repository, 
                               ReactiveMongoTemplate reactiveMongoTemplate ) {
        this.mapper = mapper;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.repository = repository;
    }

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
     * @param id Long
     * @return Mono LocationDTO
     */
    public Mono<LocationDTO> findById(Long id){
        return repository
                .findById(id)
                .map(mapper::fromModel);
    }

    /**
     * Find All Locations by City ID
     * @param cityId Long
     * @return Flux LocationDTO
     */
    public Flux<LocationDTO> findByCityId(Long cityId){
        return repository
                .findByCityId(cityId)
                .map(mapper::fromModel);
    }


    /**
     * Update Location by ID
     * @param id Long
     * @param dto LocationDTO
     * @return Mono LocationDTO
     */
    public Mono<LocationDTO> update(Long id, LocationDTO dto){
        return repository
                .save(mapper.fromDTO(dto))
                .map(mapper::fromModel);
    }

    /**
     * Delete Location by ID
     * @param id Long
     * @return Mono LocationDTO
     */
    public Mono<LocationDTO> deleteById(Long id){
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
