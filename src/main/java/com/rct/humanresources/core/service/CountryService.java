package com.rct.humanresources.core.service;

import com.rct.humanresources.core.model.dto.CountryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryService {
    Flux<CountryDTO>findAll();
    Mono<CountryDTO>findById(Long id);
    Mono<CountryDTO>update(Long id, CountryDTO dto);
    Mono<CountryDTO>deleteById(Long id);
    Flux<CountryDTO>fetchByName(String name);
    Mono<CountryDTO>create(CountryDTO dto);
}
