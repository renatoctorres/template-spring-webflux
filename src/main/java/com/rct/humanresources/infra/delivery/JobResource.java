package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.JobDTO;
import com.rct.humanresources.core.service.JobService;
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
 * Job Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jobs")
public class JobResource {
    private final JobService service;

    /**
     * Create Job
     * POST - Http Method
     *
     * @param dto JobDTO
     *
     * @return Mono JobDTO
     */
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Save Job in DB, by JobDTO Request", summary = "Create Job")
    public Mono<ResponseEntity<JobDTO>> create(@RequestBody JobDTO dto) {
        return service.create(dto)
                .map(item -> status(CREATED).body(item))
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Find All Jobs
     * GET - Http Method
     *
     * @return Flux ResponseEntity JobDTO
     */
    @GetMapping
    @Operation(description = "Find All Jobs registered", summary = "Find All Jobs")
    public Mono<ResponseEntity<List<JobDTO>>> findAll() {
        return service.findAll()
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }

    /**
     * Find Job by ID
     * GET - Http Method
     *
     * @param id String
     *
     * @return Mono ResponseEntity JobDTO
     */
    @GetMapping("/{id}")
    @Operation(description = "Find All Jobs by Job ID", summary = "Find Jobs by Job")
    public Mono<ResponseEntity<JobDTO>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Update Job by ID
     * PUT - Http Method
     *
     * @param id  String
     * @param dto JobDTO
     *
     * @return Mono ResponseEntity JobDTO
     */
    @PutMapping("/{id}")
    @Operation(description = "Update Job by ID", summary = "Update Job")
    public Mono<ResponseEntity<JobDTO>> updateById(@PathVariable String id, @RequestBody JobDTO dto) {
        return service.updateById(id, dto)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> error(new ResourceBadRequestException(e.getMessage())));
    }

    /**
     * Delete Job by ID
     * DELETE - Http Method
     *
     * @param id String
     *
     * @return Mono ResponseEntity JobDTO
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete Job by ID", summary = "Delete Job")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return service.deleteById(id)
                .map(r -> ok().<Void>build())
                .switchIfEmpty(error(new ResourceNotFoundException(id)));
    }

    /**
     * Find Jobs by Name
     * GET - Http Method
     *
     * @param title String
     *
     * @return Flux JobDTO
     */
    @GetMapping("/search")
    @Operation(description = "Search Jobs by Title", summary = "Search Jobs")
    public Mono<ResponseEntity<List<JobDTO>>> fetchByTitle(@RequestParam("title") String title) {
        return service.fetchByName(title)
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }

    /**
     * Stream all Jobs
     * GET - Http Method
     *
     * @return FLux JobDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "Stream All Jobs", summary = "Stream Jobs")
    public Mono<ResponseEntity<List<JobDTO>>> stream() {
        return service
                .findAll()
                .flatMap(job -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> job))
                        )
                                .map(Tuple2::getT2)
                )
                .collectList()
                .map(list -> new ResponseEntity<>(list, OK));
    }
}
