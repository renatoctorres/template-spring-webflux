package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.StateDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StateService {
    Flux<StateDTO> findAll();
    Flux<StateDTO> findByCountryId(Long countryId);
    Mono<StateDTO> findById(Long id);
    Mono<StateDTO> update(Long id, StateDTO dto);
    Mono<StateDTO> deleteById(Long id);
    Flux<StateDTO> fetchByName(String name);
    Mono<StateDTO> create(StateDTO dto);
}
