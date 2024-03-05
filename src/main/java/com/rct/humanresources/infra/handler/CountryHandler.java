package com.rct.humanresources.infra.handler;

import com.rct.humanresources.core.model.dto.CountryDTO;
import com.rct.humanresources.core.service.CountryService;
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
 * CountryHandler - WebFlux Handler
 */
@Component
@RequiredArgsConstructor
public class CountryHandler {
    private final CountryService service;

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
                        .body(service.create(country),CountryDTO.class))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Country by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> deleteById(ServerRequest request){
        var id = request.pathVariable("id");
        return service.deleteById(id)
                .flatMap(countryDTO -> ok().body(countryDTO, CountryDTO.class))
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
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
     * Find Country by ID
     * @param request ServerRequest
     * @return Mono ServerResponse
     */
    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = request.pathVariable("id");
        return service
                .findById(id)
                .flatMap(countryDTO -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(countryDTO, CountryDTO.class)
                )
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
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
                .orElseGet(() -> error(new ResourceNotFoundException()));

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
                        .body(service.updateById(request.pathVariable("id"), country), CountryDTO.class)
                )
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

}
