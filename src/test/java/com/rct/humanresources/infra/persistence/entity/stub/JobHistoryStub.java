package com.rct.humanresources.infra.persistence.entity.stub;

import com.rct.humanresources.infra.persistence.model.JobHistory;

import java.time.LocalDateTime;

public class JobHistoryStub {
    public static JobHistory any() {
        var jobHistory = new JobHistory();
        jobHistory.setId("1");
        jobHistory.setJobId("1");
        jobHistory.setDepartmentId("1");
        jobHistory.setCreatedAt(LocalDateTime.of(2010,1,1,0,1));
        jobHistory.setUpdatedAt(LocalDateTime.of(2011,1,1,0,1));
        return jobHistory;
    }

}
