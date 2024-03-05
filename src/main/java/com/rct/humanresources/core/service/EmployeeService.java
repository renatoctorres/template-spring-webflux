package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.EmployeeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Flux<EmployeeDTO> findAll();
    Flux<EmployeeDTO> findByDepartmentId(String departmentId);
    Flux<EmployeeDTO> findByManagerId(String managerId);
    Flux<EmployeeDTO> findByJobId(String jobId);
    Mono<EmployeeDTO> findById(String id);
    Mono<EmployeeDTO> updateById(String id, EmployeeDTO dto);
    Mono<EmployeeDTO> deleteById(String id);
    Flux<EmployeeDTO> fetchByName(String name);
    Mono<EmployeeDTO> create(EmployeeDTO dto);

}
