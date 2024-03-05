package com.rct.humanresources.infra.persistence.entity.stub;

import com.rct.humanresources.infra.persistence.model.State;

import java.time.LocalDateTime;

public class StateStub {
    public static State any(){
        var state = new State();
        state.setId("1");
        state.setName("State");
        state.setCountryId("1");
        state.setCreatedAt(LocalDateTime.of(2010,1,1,0,1));
        state.setUpdatedAt(LocalDateTime.of(2011,1,1,0,1));
        return state;
    }
}
