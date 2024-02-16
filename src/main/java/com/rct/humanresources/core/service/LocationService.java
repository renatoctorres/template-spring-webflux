package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.LocationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LocationService {
    Flux<LocationDTO> findAll();
    Flux<LocationDTO> findByCityId(Long cityId);
    Mono<LocationDTO> findById(Long id);
    Mono<LocationDTO> update(Long id, LocationDTO dto);
    Mono<LocationDTO> deleteById(Long id);
    Flux<LocationDTO> fetchByName(String name);
    Mono<LocationDTO> create(LocationDTO dto);
}
