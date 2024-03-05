package com.rct.humanresources.infra.persistence.repository;

import com.rct.humanresources.infra.persistence.model.JobHistory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * JobHistory Repository - ReactiveMongoDBRepository Implementation
 */
@Repository
public interface JobHistoryRepository extends ReactiveMongoRepository<JobHistory, String> {
    /**
     * Find JobHistory by Job ID
     * @param jobId String
     * @return Flux JobHistory
     */
    Flux<JobHistory> findByJobId(String jobId);

    /**
     * Find JobHistory by Employee ID
     * @param departmentId String
     * @return Flux JobHistory
     */
    Flux<JobHistory> findByDepartmentId(String departmentId);
}
