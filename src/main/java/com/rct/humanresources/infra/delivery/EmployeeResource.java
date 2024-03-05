package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.EmployeeDTO;
import com.rct.humanresources.core.service.EmployeeService;
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
 * Employee Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeResource {
    private final EmployeeService service;

    /**
     * Create Employee
     * POST - Http Method
     *
     * @param dto EmployeeDTO
     *
     * @return Mono<ResponseEntity < EmployeeDTO>>
     */
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Save Employee in DB, by EmployeeDTO Request", summary = "Create Employee")
    public Mono<ResponseEntity<EmployeeDTO>> create(@RequestBody EmployeeDTO dto) {
        return service.create(dto)
                .map(item -> status(CREATED).body(item))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Find All Employees
     * GET - Http Method
     *
     * @return Mono<ResponseEntity < List < EmployeeDTO>>>
     */
    @GetMapping
    @Operation(description = "Find All Employees registered", summary = "Find All Employees")
    public Mono<ResponseEntity<List<EmployeeDTO>>> findAll() {
        return service.findAll()
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }

    /**
     * Find All Employees by Department ID
     * GET - Http Method
     *
     * @param departmentId String
     *
     * @return Mono<ResponseEntity < List < EmployeeDTO>>>
     */
    @GetMapping("/departments/{departmentId}")
    @Operation(description = "Find All Employees by Department ID", summary = "Find Employees by Department")
    public Mono<ResponseEntity<List<EmployeeDTO>>> findByDepartmentId(@PathVariable String departmentId) {
        return service.findByDepartmentId(departmentId)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(departmentId)));
    }

    /**
     * Find All Employees by Manager ID
     * GET - Http Method
     *
     * @param managerId String
     *
     * @return Mono<ResponseEntity < List < EmployeeDTO>>>
     */
    @GetMapping("/managers/{managerId}")
    @Operation(description = "Find All Employees by Manager ID", summary = "Find Employees by Manager")
    public Mono<ResponseEntity<List<EmployeeDTO>>> findByManagerId(@PathVariable String managerId) {
        return service.findByManagerId(managerId)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(managerId)));
    }

    /**
     * Find All Employees by Job ID
     *
     * @param jobId String
     *
     * @return Mono<ResponseEntity < List < EmployeeDTO>>>
     */
    @GetMapping("/jobs/{jobId}")
    @Operation(description = "Find All Employees by Job ID", summary = "Find Employees by Job")
    public Mono<ResponseEntity<List<EmployeeDTO>>> findByJobId(@PathVariable String jobId) {
        return service.findByJobId(jobId)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(jobId)));
    }

    /**
     * Find Employee by ID
     * GET - Http Method
     *
     * @param id String
     *
     * @return Mono<ResponseEntity < EmployeeDTO>>
     */
    @GetMapping("/{id}")
    @Operation(description = "Find Employee by ID", summary = "Find Employee")
    public Mono<ResponseEntity<EmployeeDTO>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Update Employee by ID
     * PUT - Http Method
     *
     * @param id          String
     * @param employeeDTO EmployeeDTO
     *
     * @return Mono<ResponseEntity < EmployeeDTO>>
     */
    @PutMapping("/{id}")
    @Operation(description = "Update Employee by ID", summary = "Update Employee")
    public Mono<ResponseEntity<EmployeeDTO>> updateById(@PathVariable String id, @RequestBody EmployeeDTO employeeDTO) {
        return service.updateById(id, employeeDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Employee by ID
     * DELETE - Http Method
     *
     * @param id String
     *
     * @return Mono<ResponseEntity < EmployeeDTO>>
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete Employee by ID", summary = "Delete Employee")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return service.deleteById(id)
                .map(r -> ok().<Void>build())
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Find Employee by Name
     * GET - Http Method
     *
     * @param name String
     *
     * @return Mono<ResponseEntity < List < EmployeeDTO>>>DTO
     */
    @GetMapping("/search")
    @Operation(description = "Search Employees by Name", summary = "Search Employees")
    public Mono<ResponseEntity<List<EmployeeDTO>>> fetchByName(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(name)));
    }

    /**
     * Stream All Employees
     * GET - Http Method
     *
     * @return FLux EmployeeDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "Stream All Employees", summary = "Stream Employees")
    public Mono<ResponseEntity<List<EmployeeDTO>>> stream() {
        return service
                .findAll()
                .flatMap(employee -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> employee))
                        )
                                .map(Tuple2::getT2)
                )
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }
}