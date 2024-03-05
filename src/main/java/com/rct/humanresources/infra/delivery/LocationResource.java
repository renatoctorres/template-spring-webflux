package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.LocationDTO;
import com.rct.humanresources.core.service.LocationService;
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
 * Location Rest Controller - API Rest
 */

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationResource {
    private final LocationService service;

    /**
     * Create Location
     * POST - Http Method
     * @param dto LocationDTO
     * @return Mono ResponseEntity LocationDTO
     */
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Save Location in DB, by LocationDTO Request", summary = "Create Location")
    public Mono<ResponseEntity<LocationDTO>> create(@RequestBody LocationDTO dto){
        return service.create(dto)
                .map(item -> status(CREATED).body(item) )
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Find All Locations
     * GET - Http Method
     * @return Flux ResponseEntity LocationDTO
     */
    @GetMapping
    @Operation(description = "Find All Locations registered", summary = "Find All Locations")
    public Mono<ResponseEntity<List<LocationDTO>>> findAll(){
        return service.findAll()
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }

    /**
     * Find All Locations by City ID
     * GET - Http Method
     * @param cityId String
     * @return Flux ResponseEntity LocationDTO
     */
    @GetMapping("/cities/{cityId}")
    @Operation(description = "Find All Locations by City ID", summary = "Find Locations by City")
    public Mono<ResponseEntity<List<LocationDTO>>> findByCityId(@PathVariable String cityId){
        return service.findByCityId(cityId)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(cityId)));
    }

    /**
     * Find Location by ID
     * GET - Http Method
     * @param id String
     * @return Mono ResponseEntity LocationDTO
     */
    @GetMapping("/{id}")
    @Operation(description = "Find Location by ID", summary = "Find Location")
    public Mono<ResponseEntity<LocationDTO>> findById(@PathVariable String id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Update Location by ID
     * PUT - Http Method
     * @param id String
     * @param dto LocationDTO
     * @return Mono ResponseEntity LocationDTO
     */
    @PutMapping("/{id}")
    @Operation(description = "Update Location by ID", summary = "Update Location")
    public Mono<ResponseEntity<LocationDTO>> updateById(@PathVariable String id, @RequestBody LocationDTO dto){
        return service.updateById(id,dto)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Location by ID
     * DELETE - Http Method
     * @param id String
     * @return Mono ResponseEntity LocationDTO
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete Location by ID", summary = "Delete Location")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        return service.deleteById(id)
                .map( r -> ok().<Void>build())
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Find Locations by Name
     * GET - Http Method
     * @param street String
     * @return Flux LocationDTO LocationDTO
     */
    @GetMapping("/search")
    @Operation(description = "Search Locations by Name", summary = "Search Locations")
    public Mono<ResponseEntity<List<LocationDTO>>> fetchByName(@RequestParam("street") String street) {
        return service.fetchByName(street)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }

    /**
     * Stream all Locations
     * GET - Http Method
     * @return FLux LocationDTO LocationDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "Stream All Locations", summary = "Stream Locations")
    public Mono<ResponseEntity<List<LocationDTO>>> stream() {
        return service
                .findAll()
                .flatMap(location -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> location))
                        )
                                .map(Tuple2::getT2)
                )
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }
}