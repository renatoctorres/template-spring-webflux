package com.rct.humanresources.core.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Department DTO
 */
@Getter
@Setter
@RequiredArgsConstructor
public class DepartmentDTO {
    String id;
    String name;
    String description;
    String locationId;
    String managerId;
    String createdAt;
    String updatedAt;
}
