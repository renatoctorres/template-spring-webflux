package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.LocationDTO;
import com.rct.humanresources.core.service.LocationService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import static java.time.Duration.ofSeconds;
import static java.util.stream.Stream.generate;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static reactor.core.publisher.Flux.fromStream;
import static reactor.core.publisher.Flux.interval;
import static reactor.core.publisher.Flux.zip;

/**
 * Locations Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/locations")
public class LocationResource {
    LocationService service;

    /**
     * Create Location
     * POST - Http Method
     * @param dto LocationDTO
     * @return Mono LocationDTO
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<LocationDTO> create(@RequestBody LocationDTO dto){
        return service.create(dto);
    }

    /**
     * Find All Locations
     * GET - Http Method
     * @return Flux LocationDTO
     */
    @GetMapping
    public Flux<LocationDTO> findAll(){
        return service.findAll();
    }

    /**
     * Find All Locations by Department ID
     * GET - Http Method
     * @param cityId Long
     * @return Flux LocationDTO
     */
    @GetMapping("/cities/{cityId}")
    public Flux<ResponseEntity<LocationDTO>> findByCityId(@PathVariable Long cityId){
        return service.findByCityId(cityId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Find Location by ID
     * GET - Http Method
     * @param id Long
     * @return Mono ResponseEntity
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<LocationDTO>> findById(@PathVariable Long id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Update Location by ID
     * PUT - Http Method
     * @param id Long
     * @param dto LocationDTO
     * @return Mono ResponseEntity
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<LocationDTO>> updateById(@PathVariable Long id, @RequestBody LocationDTO dto){
        return service.update(id,dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(badRequest().build());
    }

    /**
     * Delete Location by ID
     * DELETE - Http Method
     * @param id Long
     * @return Mono ResponseEntity
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable Long id){
        return service.deleteById(id)
                .map( r -> ok().<Void>build())
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Find Locations by Name
     * GET - Http Method
     * @param name String
     * @return Flux LocationDTO
     */
    @GetMapping("/search")
    public Flux<ResponseEntity<LocationDTO>> fetchByName(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Stream all Locations
     * GET - Http Method
     * @return FLux LocationDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<LocationDTO>> stream() {
        return service
                .findAll()
                .flatMap(location -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> location))
                        )
                                .map(Tuple2::getT2)
                )
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }
}