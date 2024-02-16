package com.rct.humanresources.infra.persistence.repository;

import com.rct.humanresources.infra.persistence.model.Location;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * Location Repository - ReactiveMongoDBRepository Implementation
 */
public interface LocationRepository  extends ReactiveMongoRepository<Location, Long> {
    Flux<Location> findByCityId(Long cityId);
}
