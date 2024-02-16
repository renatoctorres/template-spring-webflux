package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.EmployerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployerService {
    Flux<EmployerDTO> findAll();
    Flux<EmployerDTO> findByDepartmentId(Long departmentId);
    Flux<EmployerDTO> findByManagerId(Long managerId);
    Flux<EmployerDTO> findByJobId(Long jobId);
    Mono<EmployerDTO> findById(Long id);
    Mono<EmployerDTO> update(Long id, EmployerDTO dto);
    Mono<EmployerDTO> deleteById(Long id);
    Flux<EmployerDTO> fetchByName(String name);
    Mono<EmployerDTO> create(EmployerDTO dto);

}
