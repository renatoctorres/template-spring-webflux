package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.CountryDTO;
import com.rct.humanresources.core.service.CountryService;
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
 * Countrys Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/countries")
public class CountryResource {
    CountryService service;

    /**
     * Create Country
     * POST - Http Method
     * @param dto CountryDTO
     * @return Mono CountryDTO
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<CountryDTO> create(@RequestBody CountryDTO dto){
        return service.create(dto);
    }

    /**
     * Find All Cities
     * GET - Http Method
     * @return Flux Country
     */
    @GetMapping
    public Flux<CountryDTO> findAll(){
        return service.findAll();
    }

    /**
     * Find Country by ID
     * GET - Http Method
     * @param id Long
     * @return Mono ResponseEntity
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<CountryDTO>> findById(@PathVariable Long id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Update Country by ID
     * PUT - Http Method
     * @param id Long
     * @param dto CountryDTO
     * @return Mono ResponseEntity
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<CountryDTO>> updateById(@PathVariable Long id, @RequestBody CountryDTO dto){
        return service.update(id,dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(badRequest().build());
    }

    /**
     * Delete Country by ID
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
     * Find Country by Name
     * GET - Http Method
     * @param name String
     * @return Flux CountryDTO
     */
    @GetMapping("/search")
    public Flux<ResponseEntity<CountryDTO>> fetchByName(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Stream All Countries
     * GET - Http Method
     * @return FLux CountryDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<CountryDTO>> stream() {
        return service
                .findAll()
                .flatMap(country -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> country))
                        )
                                .map(Tuple2::getT2)
                )
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }
}