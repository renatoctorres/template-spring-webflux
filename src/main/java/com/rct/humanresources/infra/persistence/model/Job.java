package com.rct.humanresources.infra.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Job - Spring Data Entity
 */
@Getter
@Setter
@Document(collection = "jobs")
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    private Long id;
    private String title;
    private double minSalary;
    private double maxSalary;
}
