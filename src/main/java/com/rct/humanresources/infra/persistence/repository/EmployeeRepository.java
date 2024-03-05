package com.rct.humanresources.infra.persistence.repository;

import com.rct.humanresources.infra.persistence.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Employee Repository - ReactiveMongoRepository Implementation
 */
@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
    /**
     * Find All Employees by Department ID
     * @param departmentId String
     * @return Flux Employee
     */
    Flux<Employee> findByDepartmentId(String departmentId);

    /**
     * Find All Employees by Manager ID
     * @param managerId String
     * @return Flux Employee
     */
    Flux<Employee> findByManagerId(String managerId);

    /**
     * ind All Employees by Job ID
     * @param jobId String
     * @return Flux Employee
     */
    Flux<Employee> findByJobId(String jobId);
}
