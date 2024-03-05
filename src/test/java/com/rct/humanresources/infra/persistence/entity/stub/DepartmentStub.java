package com.rct.humanresources.infra.persistence.entity.stub;

import com.rct.humanresources.infra.persistence.model.Department;

import java.time.LocalDateTime;

public class DepartmentStub {
    public static Department any(){
        var department = new Department();
        department.setId("1");
        department.setName("HR");
        department.setDescription("Human Resources");
        department.setManagerId("1");
        department.setLocationId("1");
        department.setCreatedAt(LocalDateTime.of(2010,1,1,0,1));
        department.setUpdatedAt(LocalDateTime.of(2011,1,1,0,1));
        return department;
    }
}
