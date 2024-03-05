package com.rct.humanresources.core.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Country DTO
 */
@Getter
@Setter
@RequiredArgsConstructor
public class CountryDTO {
    String id;
    String name;
    String createdAt;
    String updatedAt;
}
