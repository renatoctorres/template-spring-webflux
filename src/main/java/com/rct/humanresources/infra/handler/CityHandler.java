package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.CityDTO;
import com.rct.humanresources.core.service.CityService;
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
 * CityHandler - WebFlux Handler
 */
@Component
public class CityHandler {
    CityService service;

    /**
     * City Handler - Constructor
     * @param service CityService
     */
    @Autowired
    public CityHandler(CityService service) {
        this.service = service;
    }

    /**
     * Find All Cities
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll(), CityDTO.class);
    }

    /**
     * Find Departments by State ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findByStateId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findByStateId(Long.valueOf(request.pathVariable("stateId")))
                        , CityDTO.class);
    }

    /**
     * Stream all Cities
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> stream(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll()
                        .flatMap(city -> zip(interval(ofSeconds(2)),fromStream(generate(() -> city)))
                                .map(Tuple2::getT2)), CityDTO.class);
    }

    /**
     * Search Cities by Name
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
                        .flatMap(city ->
                                        zip(interval(ofSeconds(2)), fromStream(generate(() -> city)))
                                        .map(Tuple2::getT2)), CityDTO.class))
                .orElseGet(() -> ServerResponse.notFound().build());

    }

    /**
     * Find City by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        return service
                .findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(cityDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(cityDTO, CityDTO.class)
                )
                .switchIfEmpty(notFound().build());
    }

    /**
     * Create City
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request
                .bodyToMono(CityDTO.class)
                .flatMap(city -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(city), CityDTO.class));
    }
    /**
     * Update City by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(CityDTO.class)
                .flatMap(city -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.update(Long.valueOf(request.pathVariable("id")), city), CityDTO.class)
                );
    }

    /**
     * Delete City by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        return service.deleteById(Long.valueOf(request.pathVariable("id")))
                .flatMap(cityDTO -> ok().body(cityDTO, CityDTO.class))
                .switchIfEmpty(notFound().build());
    }
}
