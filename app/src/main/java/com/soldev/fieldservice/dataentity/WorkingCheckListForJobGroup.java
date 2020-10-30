package com.soldev.fieldservice.dataentity;

import java.util.List;

public class WorkingCheckListForJobGroup {

    private int rowNumber;
    private String Description;
    private List<WorkingCheckListForJob> workingCheckListForJobs;

    public WorkingCheckListForJobGroup(int rowNumber, String description, List<WorkingCheckListForJob> workingCheckListForJobs) {
        this.rowNumber = rowNumber;
        Description = description;
        this.workingCheckListForJobs = workingCheckListForJobs;
    }

    public WorkingCheckListForJobGroup() {
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

    public List<WorkingCheckListForJob> getWorkingCheckListForJobs() {
        return workingCheckListForJobs;
    }

    public void setWorkingCheckListForJobs(List<WorkingCheckListForJob> workingCheckListForJobs) {
        this.workingCheckListForJobs = workingCheckListForJobs;
    }
}
