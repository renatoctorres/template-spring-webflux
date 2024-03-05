package com.rct.humanresources.core.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * JobHistory DTO
 */
@Getter
@Setter
@RequiredArgsConstructor
public class JobHistoryDTO {
    String id;
    String startDate;
    String endDate;
    String jobId;
    String departmentId;
    String createdAt;
    String updatedAt;
}
