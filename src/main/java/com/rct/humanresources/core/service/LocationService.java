package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.LocationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LocationService {
    Flux<LocationDTO> findAll();
    Flux<LocationDTO> findByCityId(String cityId);
    Mono<LocationDTO> findById(String id);
    Mono<LocationDTO> updateById(String id, LocationDTO dto);
    Mono<LocationDTO> deleteById(String id);
    Flux<LocationDTO> fetchByName(String name);
    Mono<LocationDTO> create(LocationDTO dto);
}
