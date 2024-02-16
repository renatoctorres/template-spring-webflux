package com.rct.humanresources.infra.persistence.repository;

import com.rct.humanresources.infra.persistence.model.City;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * City Repository - ReactiveMongoDBRepository Implementation
 */
@Repository
public interface CityRepository extends ReactiveMongoRepository<City, Long>{
    /**
     * Find All Cities by State ID
     * @param stateId Long
     * @return Flux Employer
     */
    Flux<City> findByStateId(Long stateId);
}
