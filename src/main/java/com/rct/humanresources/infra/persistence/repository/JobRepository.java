package com.rct.humanresources.infra.persistence.repository;

import com.rct.humanresources.infra.persistence.model.Job;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Job Repository - ReactiveMongoDBRepository Implementation
 */
@Repository
public interface JobRepository extends ReactiveMongoRepository<Job, String> {
}
