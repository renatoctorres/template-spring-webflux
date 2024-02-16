package com.rct.humanresources.core.mapper;

import com.rct.humanresources.core.model.dto.EmployerDTO;
import com.rct.humanresources.infra.persistence.model.Employer;
import org.mapstruct.Mapper;

/**
 *  Employer - Mapper MapStruct Interface
 */
@Mapper(componentModel = "spring")
public interface EmployerMapper {
    /**
     * Convert Employer DTO to Model
     * @param employerDTO Employer DTO
     * @return Employer Model
     */
    Employer fromDTO(EmployerDTO employerDTO);
    /**
     * Convert Employer Model to DTO
     * @param employer Employer Model
     * @return Employer DTO
     */
    EmployerDTO fromModel(Employer employer);
}
