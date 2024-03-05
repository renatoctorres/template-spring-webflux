package com.rct.humanresources.core.model.mapper;

import com.rct.humanresources.core.model.dto.CityDTO;
import com.rct.humanresources.infra.persistence.model.City;
import org.mapstruct.Mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.factory.Mappers.getMapper;

/**
 *  City - Mapper MapStruct Interface
 */
@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR)
public interface CityMapper {
    CityMapper MAPPER = getMapper( CityMapper.class );
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