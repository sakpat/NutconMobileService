package com.soldev.fieldservice.dataentity;

import java.util.List;

public class TaskDataWorkingList {

    private int rowNumber;
    private String description;
    private List<TaskDataWorking> taskDataWorkings;

    public TaskDataWorkingList() {
    }

    public TaskDataWorkingList(int rowNumber, String description, List<TaskDataWorking> taskDataWorkings) {
        this.rowNumber = rowNumber;
        this.description = description;
        this.taskDataWorkings = taskDataWorkings;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TaskDataWorking> getTaskDataWorkings() {
        return taskDataWorkings;
    }

    public void setTaskDataWorkings(List<TaskDataWorking> taskDataWorkings) {
        this.taskDataWorkings = taskDataWorkings;
    }
}
