package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import com.rct.humanresources.core.service.JobHistoryService;
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
 * Job History Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jobs/histories")
public class JobHistoryResource {
    private final JobHistoryService service;

    /**
     * Create JobHistory
     * POST - Http Method
     *
     * @param dto JobHistoryDTO
     *
     * @return Mono<ResponseEntity < JobHistoryDTO>>
     */
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Save JobHistory in DB, by JobHistoryDTO Request", summary = "Create JobHistory")
    public Mono<ResponseEntity<JobHistoryDTO>> create(@RequestBody JobHistoryDTO dto) {
        return service.create(dto)
                .map(item -> status(CREATED).body(item))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Find All Job Histories
     * GET - Http Method
     *
     * @return Mono<ResponseEntity < List < JobHistoryDTO>>>
     */
    @GetMapping
    @Operation(description = "Find All Job Histories registered", summary = "Find All Job Histories")
    public Mono<ResponseEntity<List<JobHistoryDTO>>> findAll() {
        return service.findAll()
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }

    /**
     * Find All Job Histories by Department ID
     * GET - Http Method
     *
     * @param departmentId String
     *
     * @return Mono<ResponseEntity < List < JobHistoryDTO>>>
     */
    @GetMapping("/departments/{departmentId}")
    @Operation(description = "Find All Job Histories by Department ID", summary = "Find Job Histories by Department")
    public Mono<ResponseEntity<List<JobHistoryDTO>>> findByDepartmentId(@PathVariable String departmentId) {
        return service.findByDepartmentId(departmentId)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(departmentId)));
    }

    /**
     * Find All Job Histories by Job ID
     * GET - Http Method
     *
     * @param jobId String
     *
     * @return Mono<ResponseEntity < List < JobHistoryDTO>>>
     */
    @GetMapping("/jobs/{jobId}")
    @Operation(description = "Find All Job Histories by Job ID", summary = "Find Job Histories by Job")
    public Mono<ResponseEntity<List<JobHistoryDTO>>> findByJobId(@PathVariable String jobId) {
        return service.findByJobId(jobId)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK))
                .switchIfEmpty(error(new ResourceNotFoundException(jobId)));
    }

    /**
     * Find JobHistory by ID
     * GET - Http Method
     *
     * @param id String
     *
     * @return Mono ResponseEntity
     */
    @GetMapping("/{id}")
    @Operation(description = "Find All Job Histories by ID", summary = "Find Job Histories by ID")
    public Mono<ResponseEntity<JobHistoryDTO>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Update JobHistory by ID
     * PUT - Http Method
     *
     * @param id  String
     * @param dto JobHistoryDTO
     *
     * @return Mono ResponseEntity
     */
    @PutMapping("/{id}")
    @Operation(description = "Update JobHistory by ID", summary = "Update JobHistory")
    public Mono<ResponseEntity<JobHistoryDTO>> updateById(@PathVariable String id, @RequestBody JobHistoryDTO dto) {
        return service.updateById(id, dto)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete JobHistory by ID
     * DELETE - Http Method
     *
     * @param id String
     *
     * @return Mono ResponseEntity
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete JobHistory by ID", summary = "Delete JobHistory")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return service.deleteById(id)
                .map(r -> ok().<Void>build())
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }


    /**
     * Stream All JobHistories
     * GET - Http Method
     *
     * @return FLux JobHistoryDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "Stream All JobHistories", summary = "Stream JobHistories")
    public Mono<ResponseEntity<List<JobHistoryDTO>>> stream() {
        return service
                .findAll()
                .flatMap(jobHistory -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> jobHistory))
                        )
                                .map(Tuple2::getT2)
                )
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }
}