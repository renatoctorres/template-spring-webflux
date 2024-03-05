package com.rct.humanresources.infra.persistence.entity.stub;

import com.rct.humanresources.infra.persistence.model.Employee;

import java.time.LocalDateTime;

public class EmployeeStub {
    public static Employee any() {
            var employee = new Employee();
            employee.setId("1");
            employee.setBirthDate(LocalDateTime.of(1984,10,30,0,1));
            employee.setHireDate(LocalDateTime.of(2010,1,1,0,1));
            employee.setCommission(5000.00);
            employee.setDepartmentId("1");
            employee.setEmail("email@email.com");
            employee.setFirstName("First");
            employee.setLastName("Last");
            employee.setJobId("1");
            employee.setManagerId("1");
            employee.setPhoneNumber("119999999999");
            employee.setSalary(20000.00);
            employee.setCreatedAt(LocalDateTime.of(2010,1,1,0,1));
            employee.setUpdatedAt(LocalDateTime.of(2011,1,1,0,1));
            return employee;
    }
}





