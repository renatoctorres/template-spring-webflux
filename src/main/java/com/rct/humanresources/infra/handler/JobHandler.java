package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.mapper.JobMapper;
import com.rct.humanresources.core.model.dto.JobDTO;
import com.rct.humanresources.core.service.JobService;
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
 * JobHandler - WebFlux Handler
 */
@Component
public class JobHandler {
    JobService service;
    JobMapper mapper;

    /**
     * Job Handler - Constructor
     * @param service JobService
     * @param mapper JobMapper
     */
    @Autowired
    public JobHandler(JobService service, JobMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Find All Jobs
     * @param request ServerRequest
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
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        return service
                .findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(jobDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(jobDTO, JobDTO.class)
                )
                .switchIfEmpty(notFound().build());
    }

    /**
     * Search Jobs by Name
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> search(ServerRequest request) {
        return request.queryParam("title")
                .map(title -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.fetchByName(title),
                                JobDTO.class)).orElseGet(() -> ServerResponse
                        .notFound().build());
    }

    /**
     * Stream all Jobs
     * @param request ServerRequest
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
     * Create Job
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(JobDTO.class)
                .flatMap(job -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(job), JobDTO.class));
    }

    /**
     * Update Job by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(JobDTO.class)
                .flatMap(job -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.update(Long.valueOf(request.pathVariable("id")), job),
                                JobDTO.class)
                );
    }

    /**
     * Delete Job by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        return service.deleteById(Long.valueOf(request.pathVariable("id")))
                .flatMap(jobDTO -> ok().body(jobDTO, JobDTO.class))
                .switchIfEmpty(notFound().build());
    }
}
