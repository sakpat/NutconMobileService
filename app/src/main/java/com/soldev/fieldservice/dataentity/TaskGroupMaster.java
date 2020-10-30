package com.soldev.fieldservice.dataentity;

import java.util.List;

public class TaskGroupMaster {
    String taskGroupCode;
    String taskGroupDescription;
    List<TaskTypeMaster> taskTypeList;

    public TaskGroupMaster() {
    }

    public TaskGroupMaster(String taskGroupCode, String taskGroupDescription, List<TaskTypeMaster> taskTypeList) {
        this.taskGroupCode = taskGroupCode;
        this.taskGroupDescription = taskGroupDescription;
        this.taskTypeList = taskTypeList;
    }

    public String getTaskGroupCode() {
        return taskGroupCode;
    }

    public void setTaskGroupCode(String taskGroupCode) {
        this.taskGroupCode = taskGroupCode;
    }

    public String getTaskGroupDescription() {
        return taskGroupDescription;
    }

    public void setTaskGroupDescription(String taskGroupDescription) {
        this.taskGroupDescription = taskGroupDescription;
    }

    public List<TaskTypeMaster> getTaskTypeList() {
        return taskTypeList;
    }

    public void setTaskTypeList(List<TaskTypeMaster> taskTypeList) {
        this.taskTypeList = taskTypeList;
    }
}
