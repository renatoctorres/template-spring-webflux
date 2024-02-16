package com.rct.humanresources.infra.persistence.repository;


import com.rct.humanresources.infra.persistence.model.City;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class CityRepositoryTest {
    @Autowired
    private CityRepository repository;

    @Disabled
    @Test
    public void shouldSaveSingleCity() {
        var city = new City(1L,"Rio de Janeiro",1L);

        Publisher<City> setup = repository
                .deleteAll()
                .thenMany(repository.save(city));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();
    }

}
