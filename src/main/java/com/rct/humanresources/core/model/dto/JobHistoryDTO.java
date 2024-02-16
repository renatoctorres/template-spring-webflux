package com.rct.humanresources.core.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JobHistory DTO
 */
@Getter
@Setter
@RequiredArgsConstructor
public class JobHistoryDTO {
    String id;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String jobId;
    String departmentId;
}
