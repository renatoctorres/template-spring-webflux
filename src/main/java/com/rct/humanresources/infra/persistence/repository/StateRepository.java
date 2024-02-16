package com.rct.humanresources.infra.persistence.repository;

import com.rct.humanresources.infra.persistence.model.State;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * State Repository - ReactiveMongoDBRepository Implementation
 */
@Repository
public interface StateRepository extends ReactiveMongoRepository<State, Long> {

    /**
     * Find All Cities by Country ID
     * @param countryId Long
     * @return Flux State
     */
    Flux<State> findByCountryId(Long countryId);
}
