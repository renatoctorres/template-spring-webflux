package com.rct.humanresources.core.mapper;

import com.rct.humanresources.core.model.dto.JobDTO;
import com.rct.humanresources.infra.persistence.model.Job;
import org.mapstruct.Mapper;

/**
 *  Job - Mapper MapStruct Interface
 */
@Mapper(componentModel = "spring")
public interface JobMapper {
    /**
     * Convert Job DTO to Model
     * @param jobDTO Job DTO
     * @return Job Model
     */
    Job fromDTO(JobDTO jobDTO);

    /**
     * Convert Job Model to DTO
     * @param job Job Model
     * @return Job DTO
     */
    JobDTO fromModel(Job job);

}
