package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.DepartmentDTO;
import com.rct.humanresources.core.service.DepartmentService;
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
import static reactor.core.publisher.Flux.zip;
import static reactor.core.publisher.Flux.interval;
import static reactor.core.publisher.Flux.fromStream;


/**
 * Department Resource
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/departments")
public class DepartmentResource {
    DepartmentService service;

    /**
     * Create Department
     * POST - Http Method
     * @param dto DepartmentDTO
     * @return Mono Department
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<ResponseEntity<DepartmentDTO>> create(@RequestBody DepartmentDTO dto){
        return service
                .create(dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Find All Departments
     * GET - Http Method
     * @return Flux Department
     */
    @GetMapping
    public Flux<ResponseEntity<DepartmentDTO>> findAll(){
        return service.findAll()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }


    /**
     * Find All Departments by Manager ID
     * GET - Http Method
     * @param managerId Long
     * @return Flux Response Entity DepartmentDTO
     */
    @GetMapping("/managers/{managerId}")
    public Flux<ResponseEntity<DepartmentDTO>> findByManagerId(@PathVariable Long managerId){
        return service.findByManagerId(managerId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Find All Departments by Location ID
     * @param locationId Long
     * @return Flux Response Entity DepartmentDTO
     */
    @GetMapping("/locations/{locationId}")
    public Flux<ResponseEntity<DepartmentDTO>> findByLocationId(@PathVariable Long locationId){
        return service.findByLocationId(locationId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }


    /**
     * Find All Departments by ID
     * GET - Http Method
     * @param id Long
     * @return Mono ResponseEntity
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<DepartmentDTO>> findById(@PathVariable Long id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Update Department by ID
     * PUT - Http Method
     * @param id Long
     * @param dto DepartmentDTO
     * @return Mono ResponseEntity
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<DepartmentDTO>> updateById(@PathVariable Long id, @RequestBody DepartmentDTO dto){
        return service.update(id,dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(badRequest().build());
    }

    /**
     * Delete Department by ID
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
     * Find All Departments by Name
     * GET - Http Method
     * @param name String
     * @return Flux ResponseEntity
     */
    @GetMapping("/search")
    public Flux<ResponseEntity<DepartmentDTO>> search(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Stream All Departments
     * GET - Http Method
     * @return Flux ResponseEntity
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<DepartmentDTO>> stream() {
        return service
                .findAll()
                .flatMap(department -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> department))
                        )
                        .map(Tuple2::getT2)
                )
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }
}