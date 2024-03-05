package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.JobDTO;
import com.rct.humanresources.core.service.JobService;
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
 * JobHandler - WebFlux Handler
 */
@Component
@RequiredArgsConstructor
public class JobHandler {
    private final JobService service;

    /**
     * Create Job
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(JobDTO.class)
                .flatMap(job -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(job), JobDTO.class))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Job by ID
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request) {
        var id = request.pathVariable("id");
        return service.deleteById(id)
                .flatMap(jobDTO -> ok().body(jobDTO, JobDTO.class))
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
    }

    /**
     * Find All Jobs
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll(), JobDTO.class);
    }

    /**
     * Find Job by ID
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = request.pathVariable("id");
        return service
                .findById(id)
                .flatMap(jobDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(jobDTO, JobDTO.class)
                )
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
    }

    /**
     * Search Jobs by Name
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> search(ServerRequest request) {
        return request.queryParam("title")
                .map(title -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.fetchByName(title),
                                JobDTO.class))
                .orElseGet(() -> error(new ResourceNotFoundException()));
    }

    /**
     * Stream all Jobs
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> stream(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll()
                        .flatMap(job -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> job)))
                                .map(Tuple2::getT2)
                        ), JobDTO.class);
    }

    /**
     * Update Job by ID
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(JobDTO.class)
                .flatMap(job -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.updateById(request.pathVariable("id"), job),
                                JobDTO.class))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

}
