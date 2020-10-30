package com.soldev.fieldservice.dataentity;

import java.util.List;

public class EnvironmentCheckListForJobGroup {
    private int rowNumber;
    private String Description;
    private List<EnvironmentCheckListForJob> environmentCheckListForJobs;

    public EnvironmentCheckListForJobGroup() {
    }

    public EnvironmentCheckListForJobGroup(int rowNumber, String description, List<EnvironmentCheckListForJob> environmentCheckListForJobs) {
        this.rowNumber = rowNumber;
        Description = description;
        this.environmentCheckListForJobs = environmentCheckListForJobs;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<EnvironmentCheckListForJob> getEnvironmentCheckListForJobs() {
        return environmentCheckListForJobs;
    }

    public void setEnvironmentCheckListForJobs(List<EnvironmentCheckListForJob> environmentCheckListForJobs) {
        this.environmentCheckListForJobs = environmentCheckListForJobs;
    }
}
