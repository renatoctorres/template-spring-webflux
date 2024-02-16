package com.rct.humanresources.core.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Employer DTO
 */
@Getter
@Setter
@RequiredArgsConstructor
public class EmployerDTO {
    String id;
    String name;
    int age;
    double salary;
    String department;
}
