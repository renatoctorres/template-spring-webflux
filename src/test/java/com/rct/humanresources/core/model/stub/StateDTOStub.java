package com.rct.humanresources.core.model.stub;

import com.rct.humanresources.core.model.dto.StateDTO;

public class StateDTOStub {
    public static StateDTO any(){
        var state = new StateDTO();
        state.setId("1");
        state.setName("State Name");
        state.setCountryId("1");
        state.setAcronym("ST");
        state.setCreatedAt("01/01/2010");
        state.setUpdatedAt("01/01/2011");
        return state;
    }
}
