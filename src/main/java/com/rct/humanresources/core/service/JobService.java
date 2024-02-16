package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.JobDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JobService {
    Flux<JobDTO> findAll();
    Mono<JobDTO> findById(Long id);
    Mono<JobDTO> update(Long id, JobDTO dto);
    Mono<JobDTO> deleteById(Long id);
    Flux<JobDTO> fetchByName(String name);
    Mono<JobDTO> create(JobDTO dto);
}
