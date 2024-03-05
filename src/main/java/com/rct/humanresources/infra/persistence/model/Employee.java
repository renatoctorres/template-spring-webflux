package com.rct.humanresources.infra.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

/**
 * Employee - Spring Data Entity
 */
@Getter
@Setter
@Document(collection = "employees")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    private String id;
    private String departmentId;
    private String managerId;
    private String jobId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private double salary;
    private double commission;
    private LocalDateTime birthDate;
    private LocalDateTime hireDate;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}