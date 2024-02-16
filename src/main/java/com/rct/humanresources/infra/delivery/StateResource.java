package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.StateDTO;
import com.rct.humanresources.core.service.StateService;
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
 * States Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/states")
public class StateResource {
    StateService service;

    /**
     * Create State
     * POST - Http Method
     * @param dto StateDTO
     * @return Mono StateDTO
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<StateDTO> create(@RequestBody StateDTO dto){
        return service.create(dto);
    }

    /**
     * Find All States
     * GET - Http Method
     * @return Flux StateDTO
     */
    @GetMapping
    public Flux<StateDTO> findAll(){
        return service.findAll();
    }

    /**
     * Find All States by Country ID
     * GET - Http Method
     * @param countryId Long
     * @return Flux StateDTO
     */
    @GetMapping("/countries/{countryId}")
    public Flux<ResponseEntity<StateDTO>> findByCityId(@PathVariable Long countryId){
        return service.findByCountryId(countryId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Find State by ID
     * GET - Http Method
     * @param id Long
     * @return Mono ResponseEntity
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<StateDTO>> findById(@PathVariable Long id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Update State by ID
     * PUT - Http Method
     * @param id Long
     * @param dto StateDTO
     * @return Mono ResponseEntity
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<StateDTO>> updateById(@PathVariable Long id, @RequestBody StateDTO dto){
        return service.update(id,dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(badRequest().build());
    }

    /**
     * Delete State by ID
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
     * Find States by Name
     * GET - Http Method
     * @param name String
     * @return Flux StateDTO
     */
    @GetMapping("/search")
    public Flux<ResponseEntity<StateDTO>> fetchByName(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Stream all States
     * GET - Http Method
     * @return FLux StateDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<StateDTO>> stream() {
        return service
                .findAll()
                .flatMap(state -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> state))
                        )
                                .map(Tuple2::getT2)
                )
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }
}
