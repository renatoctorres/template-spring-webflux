package com.rct.humanresources.core.mapper;

import com.rct.humanresources.core.model.dto.LocationDTO;
import com.rct.humanresources.infra.persistence.model.Location;
import org.mapstruct.Mapper;

/**
 *  Location - Mapper MapStruct Interface
 */
@Mapper(componentModel = "spring")
public interface LocationMapper {
    /**
     * Convert Location DTO to Model
     * @param locationDTO Location DTO
     * @return Location Model
     */
    Location fromDTO(LocationDTO locationDTO);

    /**
     * Convert Location Model to DTO
     * @param location Location Model
     * @return Location DTO
     */
    LocationDTO fromModel(Location location);

}
