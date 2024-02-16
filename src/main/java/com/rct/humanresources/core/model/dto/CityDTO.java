package com.rct.humanresources.core.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * City DTO
 */
@Getter
@Setter
@RequiredArgsConstructor
public class CityDTO {
    String id;
    String name;
    String stateId;
}
