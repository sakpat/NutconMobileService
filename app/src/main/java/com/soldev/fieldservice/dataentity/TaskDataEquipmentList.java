package com.soldev.fieldservice.dataentity;

import java.util.List;

public class TaskDataEquipmentList {
    private int rowNumber;
    private String description;
    private List<TaskDataEquipment> taskDataEquipments;

    public TaskDataEquipmentList() {
    }

    public TaskDataEquipmentList(int rowNumber, String description, List<TaskDataEquipment> taskDataEquipments) {
        this.rowNumber = rowNumber;
        this.description = description;
        this.taskDataEquipments = taskDataEquipments;
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

    public List<TaskDataEquipment> getTaskDataEquipments() {
        return taskDataEquipments;
    }

    public void setTaskDataEquipments(List<TaskDataEquipment> taskDataEquipments) {
        this.taskDataEquipments = taskDataEquipments;
    }
}
