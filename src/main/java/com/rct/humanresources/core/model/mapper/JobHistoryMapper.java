package com.rct.humanresources.core.model.mapper;

import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import com.rct.humanresources.infra.persistence.model.JobHistory;
import org.mapstruct.Mapper;

/**
 *  JobHistory - Mapper MapStruct Interface
 */
@Mapper(componentModel = "spring")
public interface JobHistoryMapper {
    /**
     * Convert JobHistory DTO to Model
     * @param jobHistoryDTO JobHistory DTO
     * @return JobHistory Model
     */
    JobHistory fromDTO(JobHistoryDTO jobHistoryDTO);

    /**
     * Convert JobHistory Model to DTO
     * @param jobHistory JobHistory Model
     * @return JobHistory DTO
     */
    JobHistoryDTO fromModel(JobHistory jobHistory);

}
