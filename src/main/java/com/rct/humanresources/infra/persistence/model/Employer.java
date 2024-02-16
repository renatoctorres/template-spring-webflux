package com.rct.humanresources.infra.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Employer - Spring Data Entity
 */
@Getter
@Setter
@Document(collection = "employees")
@AllArgsConstructor
@NoArgsConstructor
public class Employer {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private double salary;
    private double commission;
    private LocalDateTime birthDate;
    private LocalDateTime hireDate;
    private Long departmentId;
    private Long managerId;
    private Long jobId;
}