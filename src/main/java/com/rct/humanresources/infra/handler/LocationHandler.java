package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.LocationDTO;
import com.rct.humanresources.core.service.LocationService;
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
 * LocationHandler - WebFlux Handler
 */
@Component
@RequiredArgsConstructor
public class LocationHandler {
    private final LocationService service;

    /**
     * Create Location
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(LocationDTO.class)
                .flatMap(location -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(location), LocationDTO.class))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Location by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        var id = request.pathVariable("id");
        return service.deleteById(id)
                .flatMap(locationDTO -> ok().body(locationDTO, LocationDTO.class))
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
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
                .body(service.findAll(), LocationDTO.class);
    }

    /**
     * Find Location by City ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByCityId(ServerRequest request) {
        var cityId = request.pathVariable("cityId");
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByCityId(cityId), LocationDTO.class)
                .onErrorResume(e -> error(new ResourceNotFoundException(cityId)));
    }

    /**
     * Find Location by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = request.pathVariable("id");
        return service
                .findById(id)
                .flatMap(locationDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(locationDTO, LocationDTO.class)
                )
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
    }

    /**
     * Search Location by Name
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> search(ServerRequest request) {
        return request.queryParam("name")
                .map(name -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.fetchByName(name), LocationDTO.class))
                .orElseGet(() -> error(new ResourceNotFoundException()));

    }
    /**
     * Stream all Locations
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> stream(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll()
                        .flatMap(location -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> location)))
                                .map(Tuple2::getT2)), LocationDTO.class);
    }

    /**
     * Update Location by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(LocationDTO.class)
                .flatMap(location -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.updateById(request.pathVariable("id"), location),
                                LocationDTO.class))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

}
