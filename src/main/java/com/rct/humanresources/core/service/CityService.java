package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.CityDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityService {
    Mono<CityDTO> create(CityDTO dto);
    Flux<CityDTO> findAll();
    Mono<CityDTO> findById(Long id);
    Flux<CityDTO> findByStateId(Long stateId);
    Mono<CityDTO> update(Long id, CityDTO dto);
    Mono<CityDTO> deleteById(Long id);
    Flux<CityDTO> fetchByName(String name);

}
