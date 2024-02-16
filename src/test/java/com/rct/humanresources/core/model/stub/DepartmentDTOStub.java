package com.rct.humanresources.core.model.stub;

import com.rct.humanresources.core.model.dto.DepartmentDTO;

public class DepartmentDTOStub {

    public static DepartmentDTO any(){
        var department = new DepartmentDTO();
        department.setId("1");
        department.setName("HR");
        department.setName("Human Resources");
        return department;
    }

}
