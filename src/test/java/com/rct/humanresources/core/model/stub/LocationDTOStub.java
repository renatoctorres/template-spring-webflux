package com.rct.humanresources.core.model.stub;

import com.rct.humanresources.core.model.dto.LocationDTO;

public class LocationDTOStub {
    public static LocationDTO any(){
        var location = new LocationDTO();
        location.setId("1");
        location.setCityId("1");
        location.setStreet("Street Name");
        location.setPostalCode("11111999");
        location.setCreatedAt("01/01/2010");
        location.setUpdatedAt("01/01/2011");
        return location;
    }
}
