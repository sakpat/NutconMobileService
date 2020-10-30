package com.soldev.fieldservice.dataentity;

public class TaskDataEquipment {
    private int rowNo;
    private String description;
    private int amount;
    private int checkBeforeWork ;
    private int checkAfterWork;

    public TaskDataEquipment() {
    }

    public TaskDataEquipment(int rowNo, String description, int amount, int checkBeforeWork, int checkAfterWork) {
        this.rowNo = rowNo;
        this.description = description;
        this.amount = amount;
        this.checkBeforeWork = checkBeforeWork;
        this.checkAfterWork = checkAfterWork;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCheckBeforeWork() {
        return checkBeforeWork;
    }

    public void setCheckBeforeWork(int checkBeforeWork) {
        this.checkBeforeWork = checkBeforeWork;
    }

    public int getCheckAfterWork() {
        return checkAfterWork;
    }

    public void setCheckAfterWork(int checkAfterWork) {
        this.checkAfterWork = checkAfterWork;
    }
}
