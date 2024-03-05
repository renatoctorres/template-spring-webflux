package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.CityDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityService {
    Mono<CityDTO> create(CityDTO dto);
    Flux<CityDTO> findAll();
    Mono<CityDTO> findById(String id);
    Flux<CityDTO> findByStateId(String stateId);
    Mono<CityDTO> updateById(String id, CityDTO dto);
    Mono<CityDTO> deleteById(String id);
    Flux<CityDTO> fetchByName(String name);

}
