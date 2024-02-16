package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.EmployerDTO;
import com.rct.humanresources.core.mapper.EmployerMapper;
import com.rct.humanresources.core.service.EmployerService;
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
 * EmployerHandler - WebFlux Handler
 */
@Component
public class EmployerHandler {
    EmployerService service;
    EmployerMapper mapper;

    /**
     * Employer Handler - Constructor
     * @param service DepartmentService
     * @param mapper DepartmentMapper
     */
    @Autowired
    public EmployerHandler(EmployerService service, EmployerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Find All Employees
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll(), EmployerDTO.class);
    }

    /**
     * Find Empolyer by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findById(Long.valueOf(request.pathVariable("id"))), EmployerDTO.class);
    }

    /**
     * Find Employees by Location ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByDepartmentId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByDepartmentId(Long.valueOf(request.pathVariable("departmentId"))),
                        EmployerDTO.class);
    }

    /**
     * Find Employees by Manager ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByManagerId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByManagerId(Long.valueOf(request.pathVariable("managerId"))),
                        EmployerDTO.class);
    }

    /**
     * Find Employees by Job ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByJobId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByJobId(Long.valueOf(request.pathVariable("jobId"))),
                        EmployerDTO.class);
    }

    /**
     * Search Employer by Name
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> search(ServerRequest request) {
        return request.queryParam("name")
                .map(name -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.fetchByName(name),
                                EmployerDTO.class)).orElseGet(() -> ServerResponse
                        .notFound().build());
    }

    /**
     * Stream all Employees
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> stream(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll()
                        .flatMap(employer -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> employer)))
                                .map(Tuple2::getT2)
                        ), EmployerDTO.class);
    }

    /**
     * Create Employer
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(EmployerDTO.class)
                .flatMap(employer -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(employer), EmployerDTO.class));
    }

    /**
     * Update Employer by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(EmployerDTO.class)
                .flatMap(employer -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.update(Long.valueOf(request.pathVariable("id")), employer),
                                EmployerDTO.class)
                );
    }

    /**
     * Delete Employer by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        return service.deleteById(Long.valueOf(request.pathVariable("id")))
                .flatMap(employerDTO -> ok().body(employerDTO, EmployerDTO.class))
                .switchIfEmpty(notFound().build());
    }
}
