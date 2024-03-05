package com.rct.humanresources.infra.persistence.entity.stub;

import com.rct.humanresources.infra.persistence.model.City;

import java.time.LocalDateTime;

public class CityStub {
    public static City any(){
        var city = new City();
        city.setId("1");
        city.setName("HR");
        city.setStateId("1");
        city.setCreatedAt(LocalDateTime.of(2010,1,1,0,1));
        city.setUpdatedAt(LocalDateTime.of(2011,1,1,0,1));
        return city;
    }
}
