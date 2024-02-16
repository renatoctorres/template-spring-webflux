package com.rct.humanresources.infra.persistence.repository;

import com.rct.humanresources.infra.persistence.model.Employer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Employer Repository - ReactiveMongoRepository Implementation
 */
@Repository
public interface EmployerRepository extends ReactiveMongoRepository<Employer, Long> {
    /**
     * Find All Employers by Department ID
     * @param departmentId Long
     * @return Flux Employer
     */
    Flux<Employer> findByDepartmentId(Long departmentId);

    /**
     * Find All Employers by Manager ID
     * @param managerId long
     * @return Flux Employer
     */
    Flux<Employer> findByManagerId(Long managerId);

    /**
     * ind All Employers by Job ID
     * @param jobId JobId
     * @return Flux Employer
     */
    Flux<Employer> findByJobId(Long jobId);
}
