package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.StateDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StateService {
    Flux<StateDTO> findAll();
    Flux<StateDTO> findByCountryId(String countryId);
    Mono<StateDTO> findById(String id);
    Mono<StateDTO> updateById(String id, StateDTO dto);
    Mono<StateDTO> deleteById(String id);
    Flux<StateDTO> fetchByName(String name);
    Mono<StateDTO> create(StateDTO dto);
}
