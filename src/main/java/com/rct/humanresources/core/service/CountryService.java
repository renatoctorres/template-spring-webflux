package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.CountryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryService {
    Flux<CountryDTO>findAll();
    Mono<CountryDTO>findById(String id);
    Mono<CountryDTO> updateById(String id, CountryDTO dto);
    Mono<CountryDTO>deleteById(String id);
    Flux<CountryDTO>fetchByName(String name);
    Mono<CountryDTO>create(CountryDTO dto);
}
