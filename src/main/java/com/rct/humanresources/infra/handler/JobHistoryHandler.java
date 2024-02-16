package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.mapper.JobHistoryMapper;
import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import com.rct.humanresources.core.service.JobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import static java.time.Duration.ofSeconds;
import static java.util.stream.Stream.generate;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Flux.fromStream;
import static reactor.core.publisher.Flux.zip;
import static reactor.core.publisher.Flux.interval;

/**
 * JobHistoryHandler - WebFlux Handler
 */
@Component
public class JobHistoryHandler {
    JobHistoryService service;
    JobHistoryMapper mapper;

    /**
     * JobHistory Handler - Constructor
     * @param service DepartmentService
     * @param mapper DepartmentMapper
     */
    @Autowired
    public JobHistoryHandler(JobHistoryService service, JobHistoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Find All Job Histories
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll(), JobHistoryDTO.class);
    }

    /**
     * Find All Job Histories by Job ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByJobId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByJobId(Long.valueOf(request.pathVariable("jobId"))),
                        JobHistoryDTO.class);
    }

    /**
     * Find All Job Histories by Department ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByDepartmentId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByDepartmentId(Long.valueOf(request.pathVariable("departmentId"))),
                        JobHistoryDTO.class);
    }

    /**
     * Stream all Job Histories
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> stream(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll()
                        .flatMap(jobHistory -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> jobHistory)))
                                .map(Tuple2::getT2)
                        ), JobHistoryDTO.class);
    }

    /**
     * Find Job History by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        return service
                .findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(jobHistoryDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(jobHistoryDTO, JobHistoryDTO.class)
                )
                .switchIfEmpty(notFound().build());
    }

    /**
     * Create Job History
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(JobHistoryDTO.class)
                .flatMap(jobHistory -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(jobHistory),
                                JobHistoryDTO.class));
    }

    /**
     * Update Job History by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
       return request.bodyToMono(JobHistoryDTO.class)
                .flatMap(jobHistory -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.update(Long.valueOf(request.pathVariable("id")), jobHistory),
                                JobHistoryDTO.class)
                );
    }

    /**
     * Delete Job History by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        return service.deleteById(Long.valueOf(request.pathVariable("id")))
                .flatMap(jobHistoryDTO -> ok().body(jobHistoryDTO, JobHistoryDTO.class))
                .switchIfEmpty(notFound().build());
    }
}
