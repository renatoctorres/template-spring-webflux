package com.rct.humanresources.core.model.stub;

import com.rct.humanresources.core.model.dto.DepartmentDTO;

public class DepartmentDTOStub {
    public static DepartmentDTO any(){
        var department = new DepartmentDTO();
        department.setId("1");
        department.setName("Department Name");
        department.setCreatedAt("01/01/2010");
        department.setUpdatedAt("01/01/2011");
        department.setDescription("Department Description");
        department.setLocationId("1");
        department.setManagerId("1");
        return department;
    }

}
