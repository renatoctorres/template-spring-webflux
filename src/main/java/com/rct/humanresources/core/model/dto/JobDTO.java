package com.rct.humanresources.core.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Job DTO
 */
@Getter
@Setter
@RequiredArgsConstructor
public class JobDTO {
    String id;
    String title;
    double minSalary;
    double maxSalary;
}
