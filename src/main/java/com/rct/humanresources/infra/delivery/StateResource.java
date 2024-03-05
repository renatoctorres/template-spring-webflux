package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.StateDTO;
import com.rct.humanresources.core.service.StateService;
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
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static reactor.core.publisher.Flux.fromStream;
import static reactor.core.publisher.Flux.interval;
import static reactor.core.publisher.Flux.zip;
import static reactor.core.publisher.Mono.error;

/**
 * State Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/states")
public class StateResource {
    private final StateService service;

    /**
     * Create State
     * POST - Http Method
     * @param dto StateDTO
     * @return Mono<ResponseEntity<StateDTO>>
     */
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Save State in DB, by StateDTO Request", summary = "Create State")
    public Mono<ResponseEntity<StateDTO>> create(@RequestBody StateDTO dto){
        return service.create(dto)
                .map(item -> status(CREATED).body(item) )
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Find All States
     * GET - Http Method
     * @return Mono<ResponseEntity<List<StateDTO>>>
     */
    @GetMapping
    @Operation(description = "Find All States registered", summary = "Find All States")
    public Mono<ResponseEntity<List<StateDTO>>> findAll(){
        return service.findAll()
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }

    /**
     * Find All States by Country ID
     * GET - Http Method
     * @param countryId String
     * @return Mono<ResponseEntity<List<StateDTO>>>
     */
    @GetMapping("/countries/{countryId}")
    @Operation(description = "Find All States by Country ID", summary = "Find States by Country")
    public Mono<ResponseEntity<List<StateDTO>>> findByCountryId(@PathVariable String countryId){
        return service.findByCountryId(countryId)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(countryId)));
    }

    /**
     * Find State by ID
     * GET - Http Method
     * @param id String
     * @return Mono<ResponseEntity<StateDTO>>
     */
    @GetMapping("/{id}")
    @Operation(description = "Find State by ID", summary = "Find State")
    public Mono<ResponseEntity<StateDTO>> findById(@PathVariable String id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Update State by ID
     * PUT - Http Method
     * @param id String
     * @param dto StateDTO
     * @return Mono<ResponseEntity<StateDTO>>
     */
    @PutMapping("/{id}")
    @Operation(description = "Update State by ID", summary = "Update State")
    public Mono<ResponseEntity<StateDTO>> updateById(@PathVariable String id, @RequestBody StateDTO dto){
        return service.updateById(id,dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(badRequest().build());
    }

    /**
     * Delete State by ID
     * DELETE - Http Method
     * @param id String
     * @return Mono<ResponseEntity<StateDTO>>
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete State by ID", summary = "Delete State")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        return service.deleteById(id)
                .map( r -> ok().<Void>build())
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Find States by Name
     * GET - Http Method
     * @param name String
     * @return Mono<ResponseEntity<List<StateDTO>>>
     */
    @GetMapping("/search")
    @Operation(description = "Search States by Name", summary = "Search States")
    public Mono<ResponseEntity<List<StateDTO>>> fetchByName(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }

    /**
     * Stream all States
     * GET - Http Method
     * @return FLux ResponseEntity StateDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "Stream All States", summary = "Stream States")
    public Mono<ResponseEntity<List<StateDTO>>> stream() {
        return service
                .findAll()
                .flatMap(state -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> state))
                        )
                                .map(Tuple2::getT2)
                )
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }
}
