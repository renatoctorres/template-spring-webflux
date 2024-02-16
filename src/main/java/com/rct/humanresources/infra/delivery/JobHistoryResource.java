package com.rct.humanresources.infra.delivery;

import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import com.rct.humanresources.core.service.JobHistoryService;
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
 * JobHistory Rest Controller - API Rest
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/jobs/histories")
public class JobHistoryResource {
    JobHistoryService service;

    /**
     * Create JobHistory
     * POST - Http Method
     * @param dto JobHistoryDTO
     * @return Mono JobHistoryDTO
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<JobHistoryDTO> create(@RequestBody JobHistoryDTO dto){
        return service
                .create(dto);
    }

    /**
     * Find All Cities
     * GET - Http Method
     * @return Flux JobHistory
     */
    @GetMapping
    public Flux<JobHistoryDTO> findAll(){
        return service.findAll();
    }

    /**
     * Find All Job Histories by Department ID
     * GET - Http Method
     * @param departmentId Long
     * @return Flux JobHistory
     */
    @GetMapping("/departments/{departmentId}")
    public Flux<ResponseEntity<JobHistoryDTO>> findByManagerId(@PathVariable Long departmentId){
        return service.findByDepartmentId(departmentId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Find JobHistory by ID
     * GET - Http Method
     * @param id Long
     * @return Mono ResponseEntity
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<JobHistoryDTO>> findById(@PathVariable Long id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Update JobHistory by ID
     * PUT - Http Method
     * @param id Long
     * @param dto JobHistoryDTO
     * @return Mono ResponseEntity
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<JobHistoryDTO>> updateById(@PathVariable Long id, @RequestBody JobHistoryDTO dto){
        return service.update(id,dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(badRequest().build());
    }

    /**
     * Delete JobHistory by ID
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
     * Find JobHistories by Name
     * GET - Http Method
     * @param name String
     * @return Flux JobHistoryDTO
     */
    @GetMapping("/search")
    public Flux<ResponseEntity<JobHistoryDTO>> fetchByName(@RequestParam("name") String name) {
        return service.fetchByName(name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    /**
     * Stream All JobHistories
     * GET - Http Method
     * @return FLux JobHistoryDTO
     */
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<JobHistoryDTO>> stream() {
        return service
                .findAll()
                .flatMap(jobHistory -> zip(interval(ofSeconds(2)),
                                fromStream(generate(() -> jobHistory))
                        )
                                .map(Tuple2::getT2)
                )
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }
}