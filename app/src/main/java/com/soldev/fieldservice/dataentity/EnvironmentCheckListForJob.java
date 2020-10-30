package com.soldev.fieldservice.dataentity;

public class EnvironmentCheckListForJob {
    private int rowNo;
    private String description;
    private boolean checkBefore;
    private boolean checkBetween;
    private boolean checkAfter;

    public EnvironmentCheckListForJob(int rowNo, String description, boolean checkBefore, boolean checkBetween, boolean checkAfter) {
        this.rowNo = rowNo;
        this.description = description;
        this.checkBefore = checkBefore;
        this.checkBetween = checkBetween;
        this.checkAfter = checkAfter;
    }

    public EnvironmentCheckListForJob() {
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

    public boolean isCheckBefore() {
        return checkBefore;
    }

    public void setCheckBefore(boolean checkBefore) {
        this.checkBefore = checkBefore;
    }

    public boolean isCheckBetween() {
        return checkBetween;
    }

    public void setCheckBetween(boolean checkBetween) {
        this.checkBetween = checkBetween;
    }

    public boolean isCheckAfter() {
        return checkAfter;
    }

    public void setCheckAfter(boolean checkAfter) {
        this.checkAfter = checkAfter;
    }
}
