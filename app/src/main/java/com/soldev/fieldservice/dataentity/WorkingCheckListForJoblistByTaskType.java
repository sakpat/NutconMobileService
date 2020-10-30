package com.soldev.fieldservice.dataentity;

import java.util.List;

public class WorkingCheckListForJoblistByTaskType {
    private int rowNumber;
    private String taskTypeCode;
    private String taskType;
    private String description;
    List<WorkingCheckListForJobGroup> workingCheckListForJobGroups;

    public WorkingCheckListForJoblistByTaskType() {
    }

    public WorkingCheckListForJoblistByTaskType(int rowNumber, String taskTypeCode, String taskType, String description, List<WorkingCheckListForJobGroup> workingCheckListForJobGroups) {
        this.rowNumber = rowNumber;
        this.taskTypeCode = taskTypeCode;
        this.taskType = taskType;
        this.description = description;
        this.workingCheckListForJobGroups = workingCheckListForJobGroups;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getTaskTypeCode() {
        return taskTypeCode;
    }

    public void setTaskTypeCode(String taskTypeCode) {
        this.taskTypeCode = taskTypeCode;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WorkingCheckListForJobGroup> getWorkingCheckListForJobGroups() {
        return workingCheckListForJobGroups;
    }

    public void setWorkingCheckListForJobGroups(List<WorkingCheckListForJobGroup> workingCheckListForJobGroups) {
        this.workingCheckListForJobGroups = workingCheckListForJobGroups;
    }
}
