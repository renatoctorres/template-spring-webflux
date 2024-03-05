package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.DepartmentDTO;
import com.rct.humanresources.core.service.DepartmentService;
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
 * Department Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentResource {
    private final DepartmentService service;

    /**
     * Create Department
     * POST - Http Method
     *
     * @param dto DepartmentDTO
     *
     * @return Mono<ResponseEntity < DepartmentDTO>>
     */
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Save Department in DB, by DepartmentDTO Request", summary = "Create Department")
    public Mono<ResponseEntity<DepartmentDTO>> create(@RequestBody DepartmentDTO dto) {
        return service
                .create(dto)
                .map(item -> status(CREATED).body(item))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Find All Departments
     * GET - Http Method
     *
     * @return Mono<ResponseEntity < List < DepartmentDTO>>>
     */
    @GetMapping
    @Operation(description = "Find All Departments registered", summary = "Find All Departments")
    public Mono<ResponseEntity<List<DepartmentDTO>>> findAll() {
        return service.findAll()
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }


    /**
     * Find All Departments by Manager ID
     * GET - Http Method
     *
     * @param managerId String
     *
     * @return Mono<ResponseEntity < List < DepartmentDTO>>>
     */
    @GetMapping("/managers/{managerId}")
    @Operation(description = "Find All Departments by Manager ID", summary = "Find Department by Manager")
    public Mono<ResponseEntity<List<DepartmentDTO>>> findByManagerId(@PathVariable String managerId) {
        return service.findByManagerId(managerId)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(managerId)));
    }

    /**
     * Find All Departments by Location ID
     *
     * @param locationId String
     *
     * @return Mono<ResponseEntity < List < DepartmentDTO>>>
     */
    @GetMapping("/locations/{locationId}")
    @Operation(description = "Find All Departments by Location ID", summary = "Find Department by Location")
    public Mono<ResponseEntity<List<DepartmentDTO>>> findByLocationId(@PathVariable String locationId) {
        return service.findByLocationId(locationId)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(locationId)));
    }


    /**
     * Find All Departments by ID
     * GET - Http Method
     *
     * @param id String
     *
     * @return Mono<ResponseEntity < DepartmentDTO>>
     */
    @GetMapping("/{id}")
    @Operation(description = "Find Department by ID", summary = "Find Department")
    public Mono<ResponseEntity<DepartmentDTO>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Update Department by ID
     * PUT - Http Method
     *
     * @param id  String
     * @param dto DepartmentDTO
     *
     * @return Mono<ResponseEntity < DepartmentDTO>>
     */
    @PutMapping("/{id}")
    @Operation(description = "Update Department by ID", summary = "Update Department")
    public Mono<ResponseEntity<DepartmentDTO>> updateById(@PathVariable String id, @RequestBody DepartmentDTO dto) {
        return service.updateById(id, dto)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Department by ID
     * DELETE - Http Method
     *
     * @param id String
     *
     * @return Mono<ResponseEntity < DepartmentDTO>>
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete Department by ID", summary = "Delete Department")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return service.deleteById(id)
                .map(r -> ok().<Void>build())
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Find All Departments by Name
     * GET - Http Method
     *
     * @param name String
     *
     * @return Mono<ResponseEntity < List < DepartmentDTO>>>
     */
    @GetMapping("/search")
    @Operation(description = "Search Departments by Name", summary = "Search Departments")
    public Mono<ResponseEntity<List<DepartmentDTO>>> search(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(name)));
    }

    /**
     * Stream All Departments
     * GET - Http Method
     *
     * @return Mono<ResponseEntity < List < DepartmentDTO>>>
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "Stream All Departments", summary = "Stream Departments")
    public Mono<ResponseEntity<List<DepartmentDTO>>> stream() {
        return service
                .findAll()
                .flatMap(department -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> department))
                        )
                                .map(Tuple2::getT2)
                )
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }
}