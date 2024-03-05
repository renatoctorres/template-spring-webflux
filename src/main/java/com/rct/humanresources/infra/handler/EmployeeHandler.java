package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.EmployeeDTO;
import com.rct.humanresources.core.service.EmployeeService;
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
 * EmployeeHandler - WebFlux Handler
 */
@Component
@RequiredArgsConstructor
public class EmployeeHandler {
    private final EmployeeService service;

    /**
     * Create Employee
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(EmployeeDTO.class)
                .flatMap(employee -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(employee), EmployeeDTO.class))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Employee by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        var id = request.pathVariable("id");
        return service.deleteById(id)
                .flatMap(employeeDTO -> ok().body(employeeDTO, EmployeeDTO.class))
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
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
                .body(service.findAll(), EmployeeDTO.class);
    }

    /**
     * Find Employees by Location ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByDepartmentId(ServerRequest request) {
        var departmentId = request.pathVariable("departmentId");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByDepartmentId(departmentId),
                        EmployeeDTO.class)
                .onErrorResume(e -> error(new ResourceNotFoundException(departmentId)));
    }

    /**
     * Find Employee by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = request.pathVariable("id");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findById(id), EmployeeDTO.class)
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
    }

    /**
     * Find Employees by Job ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByJobId(ServerRequest request) {
        var jobId = request.pathVariable("jobId");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByJobId(jobId),
                        EmployeeDTO.class)
                .onErrorResume(e -> error(new ResourceNotFoundException(jobId)));
    }

    /**
     * Find Employees by Manager ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByManagerId(ServerRequest request) {
        var managerId = request.pathVariable("managerId");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByManagerId(managerId),
                        EmployeeDTO.class)
                .onErrorResume(e -> error(new ResourceNotFoundException(managerId)));
    }

    /**
     * Search Employee by Name
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> search(ServerRequest request) {
        return request.queryParam("name")
                .map(name -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.fetchByName(name),
                                EmployeeDTO.class))
                .orElseGet(() -> error(new ResourceNotFoundException()));
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
                        .flatMap(employee -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> employee)))
                                .map(Tuple2::getT2)
                        ), EmployeeDTO.class);
    }

    /**
     * Update Employee by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(EmployeeDTO.class)
                .flatMap(employee -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.updateById(request.pathVariable("id"), employee),
                                EmployeeDTO.class))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

}
