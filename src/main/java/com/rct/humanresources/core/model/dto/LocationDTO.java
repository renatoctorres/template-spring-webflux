package com.rct.humanresources.core.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Location DTO
 */
@Getter
@Setter
@RequiredArgsConstructor
public class LocationDTO {
    String id;
    String street;
    String postalCode;
    String cityId;
}
