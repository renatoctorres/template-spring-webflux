package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.EmployerDTO;
import com.rct.humanresources.core.service.EmployerService;
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
 * Employers Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployerResource {
    EmployerService service;

    /**
     * Create Employer
     * POST - Http Method
     * @param dto EmployerDTO
     * @return Mono EmployerDTO
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<EmployerDTO> create(@RequestBody EmployerDTO dto){
        return service.create(dto);
    }

    /**
     * Find All Employees
     * GET - Http Method
     * @return Flux Employer
     */
    @GetMapping
    public Flux<EmployerDTO> findAll(){
        return service.findAll();
    }

    /**
     * Find All Employees by Department ID
     * GET - Http Method
     * @param departmentId Long
     * @return Flux Employer
     */
    @GetMapping("/departments/{departmentId}")
    public Flux<ResponseEntity<EmployerDTO>> findByDepartmentId(@PathVariable Long departmentId){
        return service.findByDepartmentId(departmentId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Find All Employees by Manager ID
     * GET - Http Method
     * @param managerId Long
     * @return Flux Employer
     */
    @GetMapping("/managers/{managerId}")
    public Flux<ResponseEntity<EmployerDTO>> findByManagerId(@PathVariable Long managerId){
        return service.findByManagerId(managerId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Find All Employees by Job ID
     * @param jobId Long
     * @return Flux Employer
     */
    @GetMapping("/jobs/{jobId}")
    public Flux<ResponseEntity<EmployerDTO>> findByJobId(@PathVariable Long jobId){
        return service.findByJobId(jobId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Find Employer by ID
     * GET - Http Method
     * @param id Long
     * @return Mono ResponseEntity
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<EmployerDTO>> findById(@PathVariable Long id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Update Employer by ID
     * PUT - Http Method
     * @param id Long
     * @param employerDTO EmployerDTO
     * @return Mono ResponseEntity
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<EmployerDTO>> updateById(@PathVariable Long id, @RequestBody EmployerDTO employerDTO){
        return service.update(id,employerDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(badRequest().build());
    }

    /**
     * Delete Employer by ID
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
     * Find Employer by Name
     * GET - Http Method
     * @param name String
     * @return Flux EmployerDTO
     */
    @GetMapping("/search")
    public Flux<ResponseEntity<EmployerDTO>> fetchByName(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Stream All Employees
     * GET - Http Method
     * @return FLux EmployerDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<EmployerDTO>> stream() {
        return service
                .findAll()
                .flatMap(employer -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> employer))
                        )
                        .map(Tuple2::getT2)
                )
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }
}