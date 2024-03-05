package com.rct.humanresources.core.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Employee DTO
 */
@Getter
@Setter
@RequiredArgsConstructor
public class EmployeeDTO {
    String id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    double salary;
    double commission;
    String birthDate;
    String hireDate;
    String departmentId;
    String managerId;
    String jobId;
    String createdAt;
    String updatedAt;
}
