package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.CountryDTO;
import com.rct.humanresources.core.service.CountryService;
import com.rct.humanresources.infra.config.exception.ResourceBadRequestException;
import com.rct.humanresources.infra.config.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

import static java.time.Duration.ofSeconds;
import static java.util.stream.Stream.generate;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static reactor.core.publisher.Flux.fromStream;
import static reactor.core.publisher.Flux.interval;
import static reactor.core.publisher.Flux.zip;
import static reactor.core.publisher.Mono.error;

/**
 * Country Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/countries")
public class CountryResource {
    private final CountryService service;

    /**
     * Create Country
     * POST - Http Method
     *
     * @param dto CountryDTO
     *
     * @return Mono<ResponseEntity < CountryDTO>>
     */
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Save Country in DB, by CountryDTO Request", summary = "Create Country")
    public Mono<ResponseEntity<CountryDTO>> create(@RequestBody CountryDTO dto) {
        return service.create(dto)
                .map(item -> status(CREATED).body(item))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Find All Countries
     * GET - Http Method
     *
     * @return Mono<ResponseEntity < List < CountryDTO>>>
     */
    @GetMapping
    @Operation(description = "Find All Countries registered", summary = "Find All Countries")
    public Mono<ResponseEntity<List<CountryDTO>>> findAll() {
        return service.findAll()
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }

    /**
     * Find Country by ID
     * GET - Http Method
     *
     * @param id String
     *
     * @return Mono<ResponseEntity < CountryDTO>>
     */
    @GetMapping("/{id}")
    @Operation(description = "Find Country by ID", summary = "Find Country")
    public Mono<ResponseEntity<CountryDTO>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Update Country by ID
     * PUT - Http Method
     *
     * @param id  String
     * @param dto CountryDTO
     *
     * @return Mono<ResponseEntity < CountryDTO>>
     */
    @PutMapping("/{id}")
    @Operation(description = "Update Country by ID", summary = "Update Country")
    public Mono<ResponseEntity<CountryDTO>> updateById(@PathVariable String id, @RequestBody CountryDTO dto) {
        return service.updateById(id, dto)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Country by ID
     * DELETE - Http Method
     *
     * @param id String
     *
     * @return Mono<ResponseEntity < CountryDTO>>
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete Country by ID", summary = "Delete Country")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return service.deleteById(id)
                .map(r -> ok().<Void>build())
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Find Country by Name
     * GET - Http Method
     *
     * @param name String
     *
     * @return Mono<ResponseEntity < List < CountryDTO>>>
     */
    @GetMapping("/search")
    @Operation(description = "Search Countries by Name", summary = "Search Countries")
    public Mono<ResponseEntity<List<CountryDTO>>> fetchByName(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(name)));
    }

    /**
     * Stream All Countries
     * GET - Http Method
     *
     * @return Mono<ResponseEntity < List < CountryDTO>>>
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "Stream All Countries", summary = "Stream Countries")
    public Mono<ResponseEntity<List<CountryDTO>>> stream() {
        return service
                .findAll()
                .flatMap(country -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> country))
                        )
                                .map(Tuple2::getT2)
                )
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }
}