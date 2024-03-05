package com.rct.humanresources.infra.persistence.entity.stub;

import com.rct.humanresources.infra.persistence.model.Country;

public class CountryStub {
    public static Country any(){
        var country = new Country();
        country.setId("1");
        country.setName("HR");
        return country;
    }
}
