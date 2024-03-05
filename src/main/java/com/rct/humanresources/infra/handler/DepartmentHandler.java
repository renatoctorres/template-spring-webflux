package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.DepartmentDTO;
import com.rct.humanresources.core.service.DepartmentService;
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
 * DepartmentHandler - WebFlux Handler
 */
@Component
@RequiredArgsConstructor
public class DepartmentHandler {
    private final DepartmentService service;

    /**
     * Create Department
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request
                .bodyToMono(DepartmentDTO.class)
                .flatMap(department -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(department), DepartmentDTO.class))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Department by ID
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request) {
        var id = request.pathVariable("id");
        return service.deleteById(id)
                .flatMap(departmentDTO -> ok().body(departmentDTO, DepartmentDTO.class))
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
    }

    /**
     * Find All Departments
     *
     * @param request ServerRequest
     *
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
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = request.pathVariable("id");
        return service
                .findById(id)
                .flatMap(departmentDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(departmentDTO, DepartmentDTO.class)
                )
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
    }

    /**
     * Find Departments by Location ID
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByLocationId(ServerRequest request) {
        var locationId = request.pathVariable("locationId");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByLocationId(locationId),
                        DepartmentDTO.class)
                .onErrorResume(e -> error(new ResourceNotFoundException(locationId)));
    }

    /**
     * Find Department by Manager ID
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByManagerId(ServerRequest request) {
        var managerId = request.pathVariable("managerId");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByManagerId(managerId), DepartmentDTO.class)
                .onErrorResume(e -> error(new ResourceNotFoundException(managerId)));
    }
    /**
     * Search Departments by Name
     *
     * @param request ServerRequest
     *
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
                                ), DepartmentDTO.class))
                .orElseGet(() -> error(new ResourceNotFoundException()));
    }

    /**
     * Stream all Departments
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
                        .flatMap(department -> zip(interval(ofSeconds(2)), fromStream(generate(() -> department)))
                                .map(Tuple2::getT2)), DepartmentDTO.class);
    }

    /**
     * Update Department by ID
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(DepartmentDTO.class)
                .flatMap(department -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.updateById(request.pathVariable("id"), department),
                                DepartmentDTO.class)
                )
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

}
