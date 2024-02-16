package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JobHistoryService {
    Flux<JobHistoryDTO> findAll();
    Mono<JobHistoryDTO> findById(Long id);
    Flux<JobHistoryDTO> findByDepartmentId(Long departmentId);
    Flux<JobHistoryDTO> findByJobId(Long jobId);
    Mono<JobHistoryDTO> update(Long id, JobHistoryDTO dto);
    Mono<JobHistoryDTO> deleteById(Long id);
    Flux<JobHistoryDTO> fetchByName(String name);
    Mono<JobHistoryDTO> create(JobHistoryDTO dto);
}
