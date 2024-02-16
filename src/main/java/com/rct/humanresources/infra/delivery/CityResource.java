package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.CityDTO;
import com.rct.humanresources.core.service.CityService;
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
 * Citys Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityResource {
    CityService service;

    /**
     * Create City
     * POST - Http Method
     * @param cityDTO CityDTO
     * @return Mono CityDTO
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<CityDTO> create(@RequestBody CityDTO cityDTO){
        return service.create(cityDTO);
    }

    /**
     * Find All Cities
     * GET - Http Method
     * @return Flux City
     */
    @GetMapping
    public Flux<CityDTO> findAll(){
        return service.findAll();
    }

    /**
     * Find All City by State ID
     * GET - Http Method
     * @param stateId Long
     * @return Flux City
     */
    @GetMapping("/states/{stateId}")
    public Flux<ResponseEntity<CityDTO>> findByStateId(@PathVariable Long stateId){
        return service
                .findByStateId(stateId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Find City by ID
     * GET - Http Method
     * @param id Long
     * @return Mono ResponseEntity
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<CityDTO>> findById(@PathVariable Long id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Update City by ID
     * PUT - Http Method
     * @param id Long
     * @param cityDTO CityDTO
     * @return Mono ResponseEntity
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<CityDTO>> updateById(@PathVariable Long id, @RequestBody CityDTO cityDTO){
        return service.update(id,cityDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(badRequest().build());
    }

    /**
     * Delete City by ID 
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
     * Find City by Name 
     * GET - Http Method
     * @param name String
     * @return Flux CityDTO
     */
    @GetMapping("/search")
    public Flux<ResponseEntity<CityDTO>> fetchByName(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Stream All Cities
     * GET - Http Method
     * @return FLux CityDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<CityDTO>> stream() {
        return service
                .findAll()
                .flatMap(city -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> city))
                        )
                                .map(Tuple2::getT2)
                )
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }
}