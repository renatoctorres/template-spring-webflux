package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.CityDTO;
import com.rct.humanresources.core.service.CityService;
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
 * City Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cities")
public class CityResource {
    private final CityService service;

    /**
     * Create City
     * POST - Http Method
     *
     * @param cityDTO CityDTO
     *
     * @return Mono<ResponseEntity < CityDTO>>
     */

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Save City in DB, by CityDTO Request", summary = "Create City")
    public Mono<ResponseEntity<CityDTO>> create(@RequestBody CityDTO cityDTO) {
        return service.create(cityDTO)
                .map(item -> status(CREATED).body(item))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Find All Cities
     * GET - Http Method
     *
     * @return Mono<ResponseEntity < List < CityDTO>>>
     */
    @GetMapping
    @Operation(description = "Find All Cities registered", summary = "Find All Cities")
    public Mono<ResponseEntity<List<CityDTO>>> findAll() {
        return service.findAll()
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }

    /**
     * Find All City by State ID
     * GET - Http Method
     *
     * @param stateId String
     *
     * @return Mono<ResponseEntity < List < CityDTO>>>
     */
    @GetMapping("/states/{stateId}")
    @Operation(description = "Find All Cities in the State by State ID", summary = "Find City by State")
    public Mono<ResponseEntity<List<CityDTO>>> findByStateId(@PathVariable String stateId) {
        return service
                .findByStateId(stateId)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(stateId)));
    }

    /**
     * Find City by ID
     * GET - Http Method
     *
     * @param id String
     *
     * @return Mono<ResponseEntity < CityDTO>>
     */
    @GetMapping("/{id}")
    @Operation(description = "Find City by ID", summary = "Find City")
    public Mono<ResponseEntity<CityDTO>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Update City by ID
     * PUT - Http Method
     *
     * @param id      String
     * @param cityDTO CityDTO
     *
     * @return Mono<ResponseEntity < CityDTO>>
     */
    @PutMapping("/{id}")
    @Operation(description = "Update City by ID", summary = "Update City")
    public Mono<ResponseEntity<CityDTO>> updateById(@PathVariable String id, @RequestBody CityDTO cityDTO) {
        return service.updateById(id, cityDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete City by ID
     * DELETE - Http Method
     *
     * @param id String
     *
     * @return Mono<ResponseEntity < CityDTO>>
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete City by ID", summary = "Delete City")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return service.deleteById(id)
                .map(r -> ok().<Void>build())
                .onErrorResume(e -> error(new ResourceNotFoundException(id)));
    }

    /**
     * Find City by Name
     * GET - Http Method
     *
     * @param name String
     *
     * @return Mono<ResponseEntity < List < CityDTO>>>
     */
    @GetMapping("/search")
    @Operation(description = "Search Cities by Name", summary = "Search Cities")
    public Mono<ResponseEntity<List<CityDTO>>> fetchByName(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(name)));
    }

    /**
     * Stream All Cities
     * GET - Http Method
     *
     * @return Mono<ResponseEntity < List < CityDTO>>>
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "Stream All Cities", summary = "Stream Cities")
    public Mono<ResponseEntity<List<CityDTO>>> stream() {
        return service
                .findAll()
                .flatMap(city -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> city))
                        )
                                .map(Tuple2::getT2)
                )
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }
}