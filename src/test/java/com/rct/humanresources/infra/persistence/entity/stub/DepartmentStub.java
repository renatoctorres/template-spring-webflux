package com.rct.humanresources.infra.persistence.entity.stub;

import com.rct.humanresources.infra.persistence.model.Department;

public class DepartmentStub {
    public static Department any(){
        var department = new Department();
        department.setId(1L);
        department.setName("HR");
        department.setDescription("Human Resources");
        return department;
    }
}
