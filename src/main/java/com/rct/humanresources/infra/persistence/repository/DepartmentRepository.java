package com.rct.humanresources.infra.persistence.repository;

import com.rct.humanresources.infra.persistence.model.Department;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Department Repository - ReactiveMongoDBRepository Implementation
 */
@Repository
public interface DepartmentRepository extends ReactiveMongoRepository<Department, Long> {
    /**
     * Find All Departments by Manager ID
     * @param managerId Long
     * @return Flux Department
     */
    Flux<Department> findByManagerId(Long managerId);

    /**
     * Find All Departments by Location ID
     * @param locationId Long
     * @return Flux Department
     */
    Flux<Department> findByLocationId(Long locationId);
}