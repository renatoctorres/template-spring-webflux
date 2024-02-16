package com.rct.humanresources.core.mapper;

import com.rct.humanresources.core.model.dto.CountryDTO;
import com.rct.humanresources.infra.persistence.model.Country;
import org.mapstruct.Mapper;

/**
 *  Country - Mapper MapStruct Interface
 */
@Mapper(componentModel = "spring")
public interface CountryMapper {
    /**
     * Convert Country DTO to Model
     * @param countryDTO Country DTO
     * @return Country Model
     */
    Country fromDTO(CountryDTO countryDTO);

    /**
     * Convert Country Model to DTO
     * @param country Country Model
     * @return Country DTO
     */
    CountryDTO fromModel(Country country);

}
