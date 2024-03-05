package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JobHistoryService {
    Flux<JobHistoryDTO> findAll();
    Mono<JobHistoryDTO> findById(String id);
    Flux<JobHistoryDTO> findByDepartmentId(String departmentId);
    Flux<JobHistoryDTO> findByJobId(String jobId);
    Mono<JobHistoryDTO> updateById(String id, JobHistoryDTO dto);
    Mono<JobHistoryDTO> deleteById(String id);
    Mono<JobHistoryDTO> create(JobHistoryDTO dto);
}
