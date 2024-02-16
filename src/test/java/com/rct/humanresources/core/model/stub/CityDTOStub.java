package com.rct.humanresources.core.model.stub;

import com.rct.humanresources.core.model.dto.CityDTO;

public class CityDTOStub {
    public static CityDTO any(){
        var city = new CityDTO();
        city.setId("1");
        city.setName("City Name");
        city.setStateId("2");
        return city;
    }
}
