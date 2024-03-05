package com.rct.humanresources.core.model.stub;

import com.rct.humanresources.core.model.dto.JobHistoryDTO;

public class JobHistoryDTOStub {
    public static JobHistoryDTO any(){
        var jobHistory = new JobHistoryDTO();
        jobHistory.setId("1");
        jobHistory.setDepartmentId("1");
        jobHistory.setStartDate("05/02/2020");
        jobHistory.setJobId("1");
        jobHistory.setCreatedAt("01/01/2010");
        jobHistory.setUpdatedAt("01/01/2011");
        return jobHistory;
    }
}
