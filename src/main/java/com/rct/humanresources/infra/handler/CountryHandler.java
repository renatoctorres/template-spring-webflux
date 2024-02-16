package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.mapper.CountryMapper;
import com.rct.humanresources.core.model.dto.CountryDTO;
import com.rct.humanresources.core.service.CountryService;
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
 * CountryHandler - WebFlux Handler
 */
@Component
public class CountryHandler {
    CountryService service;
    CountryMapper mapper;

    /**
     * Country Handler - Constructor
     * @param service CountryService
     * @param mapper CountryMapper
     */
    @Autowired
    public CountryHandler(CountryService service, CountryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Find All Countries
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll(), CountryDTO.class);
    }

    /**
     * Stream All Countries
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> stream(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(service.findAll()
                        .flatMap(country -> zip(interval(ofSeconds(2)),fromStream(generate(() -> country)))
                                .map(Tuple2::getT2)), CountryDTO.class);
    }

    /**
     * Search Countries by Name
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
                                        .flatMap(item -> zip(interval(ofSeconds(2)),
                                                fromStream(generate(() -> item)))
                                                .map(Tuple2::getT2)
                                        ), CountryDTO.class))
                .orElseGet(() -> ServerResponse.notFound().build());

    }

    /**
     * Find Country by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        return service
                .findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(countryDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(countryDTO, CountryDTO.class)
                )
                .switchIfEmpty(notFound().build());
    }

    /**
     * Create Country
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CountryDTO.class)
                .flatMap(country -> ServerResponse
                        .status(CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(service.create(country),CountryDTO.class));
    }

    /**
     * Update Country by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> updateById(ServerRequest request) {
        return request.bodyToMono(CountryDTO.class)
                .flatMap(country -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(service.update(Long.valueOf(request.pathVariable("id")), country), CountryDTO.class)
                );
    }

    /**
     * Delete Country by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        return service.deleteById(Long.valueOf(request.pathVariable("id")))
                .flatMap(countryDTO -> ok().body(countryDTO, CountryDTO.class))
                .switchIfEmpty(notFound().build());
    }
}
