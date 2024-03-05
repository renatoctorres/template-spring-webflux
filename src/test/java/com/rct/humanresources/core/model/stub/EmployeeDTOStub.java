package com.rct.humanresources.core.model.stub;

import com.rct.humanresources.core.model.dto.EmployeeDTO;

import java.time.LocalDateTime;

public class EmployeeDTOStub {
    public static EmployeeDTO any(){
        var employee = new EmployeeDTO();
        employee.setId("1");
        employee.setBirthDate("30/10/1984");
        employee.setCommission(5000.00);
        employee.setDepartmentId("1");
        employee.setHireDate("01/01/2010");
        employee.setEmail("email@email.com");
        employee.setFirstName("First");
        employee.setLastName("Last");
        employee.setJobId("1");
        employee.setManagerId("1");
        employee.setPhoneNumber("119999999999");
        employee.setSalary(20000.00);
        employee.setCreatedAt("01/01/2010");
        employee.setUpdatedAt("01/01/2011");
        return employee;
    }
}
