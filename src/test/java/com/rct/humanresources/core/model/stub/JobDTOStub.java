package com.rct.humanresources.core.model.stub;

import com.rct.humanresources.core.model.dto.JobDTO;

public class JobDTOStub {
    public static JobDTO any(){
        var job = new JobDTO();
        job.setId("1");
        job.setTitle("Job Title");
        job.setMaxSalary(30000.00);
        job.setMinSalary(15000.00);
        job.setCreatedAt("01/01/2010");
        job.setUpdatedAt("01/01/2011");
        return job;
    }
}
