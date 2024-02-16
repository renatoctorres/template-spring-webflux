package com.rct.humanresources.core.mapper;

import com.rct.humanresources.core.model.dto.CityDTO;
import com.rct.humanresources.infra.persistence.model.City;
import org.mapstruct.Mapper;

/**
 *  City - Mapper MapStruct Interface
 */
@Mapper(componentModel = "spring")
public interface CityMapper {
    /**
     * Convert City DTO to Model
     *
     * @param cityDTO City DTO
     * @return City Model
     */
    City fromDTO(CityDTO cityDTO);

    /**
     * Convert City Model to DTO
     *
     * @param city City Model
     * @return City DTO
     */
    CityDTO fromModel(City city);
}