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
 * Job History - Spring Data Entity
 */
@Getter
@Setter
@Document(collection = "job-histories")
@AllArgsConstructor
@NoArgsConstructor
public class JobHistory {
    @Id
    private String id;
    private String departmentId;
    private String jobId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
