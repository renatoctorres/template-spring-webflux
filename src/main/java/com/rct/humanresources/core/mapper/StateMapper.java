package com.rct.humanresources.core.mapper;

import com.rct.humanresources.core.model.dto.StateDTO;
import com.rct.humanresources.infra.persistence.model.State;
import org.mapstruct.Mapper;

/**
 *  State - Mapper MapStruct Interface
 */
@Mapper(componentModel = "spring")
public interface StateMapper {
    /**
     * Convert State DTO to Model
     * @param stateDTO State DTO
     * @return State Model
     */
    State fromDTO(StateDTO stateDTO);

    /**
     * Convert State Model to DTO
     * @param state State Model
     * @return State DTO
     */
    StateDTO fromModel(State state);

}
