package com.rct.humanresources.core.model.stub;

import com.rct.humanresources.core.model.dto.CountryDTO;

public class CountryDTOStub {
    public static CountryDTO any(){
        var country = new CountryDTO();
        country.setId("1");
        country.setName("Country Name");
        return country;
    }
}
