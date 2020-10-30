package com.soldev.fieldservice.dataentity;

import java.util.List;

public class EnvironmentCheckListForJobListByTaskType {
    private int rowNumber;
    private String taskTypeCode;
    private String taskType;
    private String description;
    private List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups;

    public EnvironmentCheckListForJobListByTaskType() {
    }

    public EnvironmentCheckListForJobListByTaskType(int rowNumber, String taskTypeCode, String taskType, String description, List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups) {
        this.rowNumber = rowNumber;
        this.taskTypeCode = taskTypeCode;
        this.taskType = taskType;
        this.description = description;
        this.environmentCheckListForJobGroups = environmentCheckListForJobGroups;
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

    public List<EnvironmentCheckListForJobGroup> getEnvironmentCheckListForJobGroups() {
        return environmentCheckListForJobGroups;
    }

    public void setEnvironmentCheckListForJobGroups(List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups) {
        this.environmentCheckListForJobGroups = environmentCheckListForJobGroups;
    }
}
