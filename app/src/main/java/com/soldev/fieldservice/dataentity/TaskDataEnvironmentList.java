package com.soldev.fieldservice.dataentity;

import java.util.List;

public class TaskDataEnvironmentList {
    private int rowNumber;
    private String description;
    private List<TaskDataEnvironment> taskDataEnvironment;

    public TaskDataEnvironmentList(int rowNumber, String description, List<TaskDataEnvironment> taskDataEnvironment) {
        this.rowNumber = rowNumber;
        this.description = description;
        this.taskDataEnvironment = taskDataEnvironment;
    }

    public TaskDataEnvironmentList() {
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

    public List<TaskDataEnvironment> getTaskDataEnvironment() {
        return taskDataEnvironment;
    }

    public void setTaskDataEnvironment(List<TaskDataEnvironment> taskDataEnvironment) {
        this.taskDataEnvironment = taskDataEnvironment;
    }
}
