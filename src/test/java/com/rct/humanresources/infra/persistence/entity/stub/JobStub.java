package com.rct.humanresources.infra.persistence.entity.stub;

import com.rct.humanresources.infra.persistence.model.Job;

import java.time.LocalDateTime;

public class JobStub {
    public static Job any() {
        var job = new Job();
        job.setId("1");
        job.setTitle("Job Title");
        job.setMaxSalary(30000.00);
        job.setMinSalary(15000.00);
        job.setCreatedAt(LocalDateTime.of(2010,1,1,0,1));
        job.setUpdatedAt(LocalDateTime.of(2011,1,1,0,1));
        return job;
    }
}
