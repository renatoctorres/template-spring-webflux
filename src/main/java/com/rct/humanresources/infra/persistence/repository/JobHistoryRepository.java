package com.rct.humanresources.infra.persistence.repository;

import com.rct.humanresources.infra.persistence.model.JobHistory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * JobHistory Repository - ReactiveMongoDBRepository Implementation
 */
@Repository
public interface JobHistoryRepository extends ReactiveMongoRepository<JobHistory, Long> {
    /**
     * Find JobHistory by Job ID
     * @param jobId Long
     * @return Flux JobHistory
     */
    Flux<JobHistory> findByJobId(Long jobId);

    /**
     * Find JobHistory by Employer ID
     * @param departmentId Long
     * @return Flux JobHistory
     */
    Flux<JobHistory> findByDepartmentId(Long departmentId);
}
