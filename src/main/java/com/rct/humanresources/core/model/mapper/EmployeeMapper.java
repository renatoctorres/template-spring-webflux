package com.rct.humanresources.core.model.mapper;

import com.rct.humanresources.core.model.dto.EmployeeDTO;
import com.rct.humanresources.infra.persistence.model.Employee;
import org.mapstruct.Mapper;

/**
 *  Employee - Mapper MapStruct Interface
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    /**
     * Convert Employee DTO to Model
     * @param employeeDTO Employee DTO
     * @return Employee Model
     */
    Employee fromDTO(EmployeeDTO employeeDTO);
    /**
     * Convert Employee Model to DTO
     * @param employee Employee Model
     * @return Employee DTO
     */
    EmployeeDTO fromModel(Employee employee);
}
