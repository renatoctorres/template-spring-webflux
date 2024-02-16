package com.rct.humanresources.core.mapper;

import com.rct.humanresources.core.model.dto.DepartmentDTO;
import com.rct.humanresources.infra.persistence.model.Department;
import org.mapstruct.Mapper;

/**
 *  Department - Mapper MapStruct Interface
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    /**
     * Convert Department DTO to Model
     * @param departmentDTO Department DTO
     * @return Department Model
     */
    Department fromDTO(DepartmentDTO departmentDTO);

    /**
     * Convert Department Model to DTO
     * @param department Department Model
     * @return Department DTO
     */
    DepartmentDTO fromModel(Department department);
}
