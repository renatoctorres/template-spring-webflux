package com.rct.humanresources.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rct.humanresources.infra.persistence.model.City;
import com.rct.humanresources.infra.persistence.model.Country;
import com.rct.humanresources.infra.persistence.model.State;
import com.rct.humanresources.infra.persistence.repository.CityRepository;
import com.rct.humanresources.infra.persistence.repository.CountryRepository;
import com.rct.humanresources.infra.persistence.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MongoDBDataInitializerConfig implements CommandLineRunner {
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final ResourceLoader resourceLoader;
    @Override
    public void run(String... args) throws Exception {
        this.insertCountries();
        this.insertStates();
        this.insertCities();
    }

    public void insertCountries() throws IOException {
        var countriesInputStream = resourceLoader.getResource("classpath:static/countries-data.json").getInputStream();
        var countries = List.of(new ObjectMapper().readValue(countriesInputStream, Country[].class));

        countryRepository.deleteAll()
                .thenMany(Flux.fromIterable(countries))
                .flatMap(countryRepository::save)
                .thenMany(countryRepository.findAll())
                .subscribe(country -> log.info(String.valueOf(country)));
    }

    public void insertStates() throws IOException {
        var statesInputStream = resourceLoader.getResource("classpath:static/states-data.json").getInputStream();
        var states = List.of(new ObjectMapper().readValue(statesInputStream, State[].class));

        stateRepository.deleteAll()
                .thenMany(Flux.fromIterable(states))
                .flatMap(stateRepository::save)
                .thenMany(stateRepository.findAll())
                .subscribe(state -> log.info(String.valueOf(state)));
    }

    public void insertCities() throws IOException {
        var citiesInputStream = resourceLoader.getResource("classpath:static/cities-data.json").getInputStream();
        var cities = List.of(new ObjectMapper().readValue(citiesInputStream, City[].class));

        cityRepository.deleteAll()
                .thenMany(Flux.fromIterable(cities))
                .flatMap(cityRepository::save)
                .thenMany(cityRepository.findAll())
                .subscribe(city -> log.info(String.valueOf(city)));
    }



}
