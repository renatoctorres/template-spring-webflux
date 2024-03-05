package com.rct.humanresources.core.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * State DTO
 */
@Getter
@Setter
@RequiredArgsConstructor
public class StateDTO {
    String id;
    String name;
    String acronym;
    String countryId;
    String createdAt;
    String updatedAt;
}
