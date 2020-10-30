package com.soldev.fieldservice.dataentity;

import java.util.List;

public class EquipmentForJobGroup {
    private int rowNumber;
    private String Description;
    private List<EquipmentForJob> equipmentForJobs;

    public EquipmentForJobGroup() {
    }

    public EquipmentForJobGroup(int rowNumber, String description, List<EquipmentForJob> equipmentForJobs) {
        this.rowNumber = rowNumber;
        Description = description;
        this.equipmentForJobs = equipmentForJobs;
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

    public List<EquipmentForJob> getEquipmentForJobs() {
        return equipmentForJobs;
    }

    public void setEquipmentForJobs(List<EquipmentForJob> equipmentForJobs) {
        this.equipmentForJobs = equipmentForJobs;
    }
}
