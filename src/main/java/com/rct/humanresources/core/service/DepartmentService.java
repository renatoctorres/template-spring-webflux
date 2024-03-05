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
    Flux<DepartmentDTO> findByManagerId(String managerId);
    Flux<DepartmentDTO> findByLocationId(String locationId);
    Mono<DepartmentDTO> findById(String id);
    Mono<DepartmentDTO> updateById(String id, DepartmentDTO dto);
    Mono<DepartmentDTO> deleteById(String id);
    Flux<DepartmentDTO> fetchByName(String name);
}
