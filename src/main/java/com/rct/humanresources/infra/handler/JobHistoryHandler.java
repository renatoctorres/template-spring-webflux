package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import com.rct.humanresources.core.service.JobHistoryService;
import com.rct.humanresources.infra.config.exception.ResourceBadRequestException;
import com.rct.humanresources.infra.config.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import static java.time.Duration.ofSeconds;
import static java.util.stream.Stream.generate;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Flux.fromStream;
import static reactor.core.publisher.Flux.interval;
import static reactor.core.publisher.Flux.zip;
import static reactor.core.publisher.Mono.error;

/**
 * JobHistoryHandler - WebFlux Handler
 */
@Component
@RequiredArgsConstructor
public class JobHistoryHandler {
    private final JobHistoryService service;

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
                        .body(service.create(jobHistory), JobHistoryDTO.class))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Job History by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        var id = request.pathVariable("id");
        return service.deleteById(id)
                .flatMap(jobHistoryDTO -> ok().body(jobHistoryDTO, JobHistoryDTO.class))
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
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
     * Find All Job Histories by Department ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByDepartmentId(ServerRequest request) {
        var departmentId = request.pathVariable("departmentId");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByDepartmentId(departmentId), JobHistoryDTO.class)
                .onErrorResume(e -> error(new ResourceNotFoundException(departmentId)));
    }

    /**
     * Find Job History by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = request.pathVariable("id");
        return service
                .findById(id)
                .flatMap(jobHistoryDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(jobHistoryDTO, JobHistoryDTO.class)
                )
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
    }

    /**
     * Find All Job Histories by Job ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByJobId(ServerRequest request) {
        var jobId = request.pathVariable("jobId");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByJobId(jobId), JobHistoryDTO.class)
                .onErrorResume(e -> error(new ResourceNotFoundException(jobId)));
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
     * Update Job History by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
       return request.bodyToMono(JobHistoryDTO.class)
                .flatMap(jobHistory -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.updateById(request.pathVariable("id"), jobHistory),
                                JobHistoryDTO.class))
               .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

}
