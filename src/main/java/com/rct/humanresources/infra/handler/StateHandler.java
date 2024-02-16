package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.StateDTO;
import com.rct.humanresources.core.mapper.StateMapper;
import com.rct.humanresources.core.service.StateService;
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
 * StateHandler - WebFlux Handler
 */
@Component
public class StateHandler {
    StateService service;
    StateMapper mapper;

    /**
     * State Handler - Constructor
     * @param service DepartmentService
     * @param mapper DepartmentMapper
     */
    @Autowired
    public StateHandler(StateService service, StateMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Find All Locations
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll(), StateDTO.class);
    }

    /**
     * Find States by Country ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByCountryId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByCountryId(Long.valueOf(request.pathVariable("countryId"))), StateDTO.class);
    }

    /**
     * Find State by  ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        return service
                .findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(stateDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(stateDTO, StateDTO.class)
                )
                .switchIfEmpty(notFound().build());
    }

    /**
     * Search State by Name
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> search(ServerRequest request) {
        return request.queryParam("name")
                .map(name -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.fetchByName(name), StateDTO.class)).orElseGet(() -> ServerResponse
                        .notFound().build());

    }
    /**
     * Stream All States
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> stream(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll()
                        .flatMap(state -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> state)))
                                .map(Tuple2::getT2)
                        ), StateDTO.class);
    }

    /**
     * Create State
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(StateDTO.class)
                .flatMap(state -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(state), StateDTO.class));
    }

    /**
     * Update State by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(StateDTO.class)
                .flatMap(state -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.update(Long.valueOf(request.pathVariable("id")), state),
                                StateDTO.class)
                );
    }

    /**
     * Delete State by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        return service.deleteById(Long.valueOf(request.pathVariable("id")))
                .flatMap(stateDTO -> ok().body(stateDTO, StateDTO.class))
                .switchIfEmpty(notFound().build());
    }
}
