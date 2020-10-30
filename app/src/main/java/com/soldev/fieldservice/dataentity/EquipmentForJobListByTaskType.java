package com.soldev.fieldservice.dataentity;

import java.util.List;

public class EquipmentForJobListByTaskType {
    private int rowNumber;
    private String taskTypeCode;
    private String taskType;
    private String description;
    private List<EquipmentForJobGroup> equipmentForJobGroups;

    public EquipmentForJobListByTaskType() {
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

    public List<EquipmentForJobGroup> getEquipmentForJobsGroup() {
        return equipmentForJobGroups;
    }

    public void setEquipmentForJobsGroup(List<EquipmentForJobGroup> equipmentForJobs) {
        this.equipmentForJobGroups = equipmentForJobs;
    }

    public EquipmentForJobListByTaskType(int rowNumber, String taskTypeCode, String taskType, String description, List<EquipmentForJobGroup> equipmentForJobGroups) {
        this.rowNumber = rowNumber;
        this.taskTypeCode = taskTypeCode;
        this.taskType = taskType;
        this.description = description;
        this.equipmentForJobGroups = equipmentForJobGroups;

    }
}
