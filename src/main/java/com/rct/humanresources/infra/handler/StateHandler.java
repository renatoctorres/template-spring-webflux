package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.StateDTO;
import com.rct.humanresources.core.service.StateService;
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
 * StateHandler - WebFlux Handler
 */
@Component
@RequiredArgsConstructor
public class StateHandler {
    private final StateService service;

    /**
     * Create State
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(StateDTO.class)
                .flatMap(state -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(state), StateDTO.class))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete State by ID
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request) {
        var id = request.pathVariable("id");
        return service.deleteById(id)
                .flatMap(stateDTO -> ok().body(stateDTO, StateDTO.class))
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
    }

    /**
     * Find All Locations
     *
     * @param request ServerRequest
     *
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
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByCountryId(ServerRequest request) {
        var countryId = request.pathVariable("countryId");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByCountryId(countryId), StateDTO.class)
                .onErrorResume(e -> error(new ResourceNotFoundException(countryId)));
    }

    /**
     * Find State by  ID
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = request.pathVariable("countryId");
        return service
                .findById(id)
                .flatMap(stateDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(stateDTO, StateDTO.class)
                )
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
    }

    /**
     * Search State by Name
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
                        .body(service.fetchByName(name), StateDTO.class))
                .orElseGet(() -> error(new ResourceNotFoundException()));
    }

    /**
     * Stream All States
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
                        .flatMap(state -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> state)))
                                .map(Tuple2::getT2)
                        ), StateDTO.class);
    }

    /**
     * Update State by ID
     *
     * @param request ServerRequest
     *
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(StateDTO.class)
                .flatMap(state -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.updateById(request.pathVariable("id"), state),
                                StateDTO.class)
                );
    }

}
