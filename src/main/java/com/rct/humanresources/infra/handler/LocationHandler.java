package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.mapper.LocationMapper;
import com.rct.humanresources.core.model.dto.LocationDTO;
import com.rct.humanresources.core.service.LocationService;
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
 * LocationHandler - WebFlux Handler
 */
@Component
public class LocationHandler {
    LocationService service;
    LocationMapper mapper;

    /**
     * Location Handler - Constructor
     * @param service DepartmentService
     * @param mapper DepartmentMapper
     */
    @Autowired
    public LocationHandler(LocationService service, LocationMapper mapper) {
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
                .body(service.findAll(), LocationDTO.class);
    }

    /**
     * Find Location by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        return service
                .findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(locationDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(locationDTO, LocationDTO.class)
                )
                .switchIfEmpty(notFound().build());
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
                        .body(service.fetchByName(name), LocationDTO.class)).orElseGet(() -> ServerResponse
                        .notFound().build());

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
                                .map(Tuple2::getT2)
                        ), LocationDTO.class);
    }

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
                        .body(service.create(location),
                                LocationDTO.class));
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
                        .body(service.update(Long.valueOf(request.pathVariable("id")), location),
                                LocationDTO.class)
                );
    }

    /**
     * Delete Location by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        return service.deleteById(Long.valueOf(request.pathVariable("id")))
                .flatMap(locationDTO -> ok().body(locationDTO, LocationDTO.class))
                .switchIfEmpty(notFound().build());
    }
}
