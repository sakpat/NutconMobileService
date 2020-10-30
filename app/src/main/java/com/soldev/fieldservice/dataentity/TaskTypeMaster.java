package com.soldev.fieldservice.dataentity;

public class TaskTypeMaster {
    String taskTypeCode;
    String taskTypeDescription;

    public TaskTypeMaster(String taskTypeCode, String taskTypeDescription) {
        this.taskTypeCode = taskTypeCode;
        this.taskTypeDescription = taskTypeDescription;
    }

    public TaskTypeMaster() {
    }

    public String getTaskTypeCode() {
        return taskTypeCode;
    }

    public void setTaskTypeCode(String taskTypeCode) {
        this.taskTypeCode = taskTypeCode;
    }

    public String getTaskTypeDescription() {
        return taskTypeDescription;
    }

    public void setTaskTypeDescription(String taskTypeDescription) {
        this.taskTypeDescription = taskTypeDescription;
    }
}
