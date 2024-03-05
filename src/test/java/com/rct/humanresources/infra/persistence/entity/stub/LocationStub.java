package com.rct.humanresources.infra.persistence.entity.stub;

import com.rct.humanresources.infra.persistence.model.Location;
import java.time.LocalDateTime;

public class LocationStub {
    public static Location any(){
        var location = new Location();
        location.setId("1");
        location.setStreet("Street Name");
        location.setPostalCode("11111999");
        location.setCreatedAt(LocalDateTime.of(2010,1,1,0,1));
        location.setUpdatedAt(LocalDateTime.of(2011,1,1,0,1));
        return location;
    }
}
