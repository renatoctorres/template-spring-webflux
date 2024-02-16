package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.DepartmentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Department Service - Interface
 */
public interface DepartmentService {
    Mono<DepartmentDTO> create(DepartmentDTO dto);
    Flux<DepartmentDTO> findAll();
    Flux<DepartmentDTO> findByManagerId(Long managerId);
    Flux<DepartmentDTO> findByLocationId(Long locationId);
    Mono<DepartmentDTO> findById(Long id);
    Mono<DepartmentDTO> update(Long id, DepartmentDTO dto);
    Mono<DepartmentDTO> deleteById(Long id);
    Flux<DepartmentDTO> fetchByName(String name);
}
