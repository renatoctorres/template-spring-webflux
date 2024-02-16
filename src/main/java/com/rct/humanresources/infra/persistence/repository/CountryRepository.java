package com.rct.humanresources.infra.persistence.repository;

import com.rct.humanresources.infra.persistence.model.Country;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Country Repository - ReactiveMongoDBRepository Implementation
 */
@Repository
public interface CountryRepository extends ReactiveMongoRepository<Country, Long> {
}
