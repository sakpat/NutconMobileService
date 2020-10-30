package com.soldev.fieldservice.dataentity;

public class EquipmentForJob {
    private int rowNo;
    private String description;
    private int units;

    public EquipmentForJob() {
    }

    public EquipmentForJob(int rowNo, String description, int units) {
        this.rowNo = rowNo;
        this.description = description;
        this.units = units;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
