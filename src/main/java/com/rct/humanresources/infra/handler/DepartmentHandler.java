package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.DepartmentDTO;
import com.rct.humanresources.core.mapper.DepartmentMapper;
import com.rct.humanresources.core.service.DepartmentService;
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
 * DepartmentHandler - WebFlux Handler
 */
@Component
public class DepartmentHandler {
    DepartmentService service;
    DepartmentMapper mapper;

    /**
     * Department Handler - Constructor
     * @param service DepartmentService
     * @param mapper DepartmentMapper
     */
    @Autowired
    public DepartmentHandler(DepartmentService service, DepartmentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Find All Departments
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll(), DepartmentDTO.class);
    }

    /**
     * Find Department by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        return service
                .findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(departmentDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(departmentDTO, DepartmentDTO.class)
                )
                .switchIfEmpty(notFound().build());
    }

    /**
     * Find Departments by Location ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByLocationId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByLocationId(Long.valueOf(request.pathVariable("locationId"))),
                        DepartmentDTO.class);
    }

    /**
     * Find Department by Manager ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByManagerId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByManagerId(Long.valueOf(request.pathVariable("managerId"))),
                        DepartmentDTO.class);
    }

    /**
     * Stream all Departments
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> stream(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll()
                        .flatMap(department -> zip(interval(ofSeconds(2)),fromStream(generate(() -> department)))
                .map(Tuple2::getT2)), DepartmentDTO.class);
    }

    /**
     * Search Departments by Name
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> search(ServerRequest request) {
        return request.queryParam("name")
                .map(name -> ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service
                        .fetchByName(name)
                        .flatMap(department -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> department)))
                                .map(Tuple2::getT2)
                        ),  DepartmentDTO.class)).orElseGet(() -> ServerResponse.notFound().build());
    }

    /**
     * Create Department
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request
                .bodyToMono(DepartmentDTO.class)
                .flatMap(department -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(department),DepartmentDTO.class));
    }

    /**
     * Update Department by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(DepartmentDTO.class)
                .flatMap(department -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.update(Long.valueOf(request.pathVariable("id")), department),
                                DepartmentDTO.class)
                );
    }

    /**
     * Delete Department by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        return service.deleteById(Long.valueOf(request.pathVariable("id")))
                .flatMap(departmentDTO -> ok().body(departmentDTO, DepartmentDTO.class))
                .switchIfEmpty(notFound().build());
    }
}
