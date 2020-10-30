package com.soldev.fieldservice.dataentity;

public class TaskDataEnvironment {
    private int rowNo;
    private String description;
    private boolean checkBefore;
    private int foundBefore;
    private String remarkBefore;
    private boolean checkBetween;
    private int foundBetween;
    private String remarkBetween;
    private boolean checkAfter;

    public int getFoundBefore() {
        return foundBefore;
    }

    public void setFoundBefore(int foundBefore) {
        this.foundBefore = foundBefore;
    }

    public int getFoundBetween() {
        return foundBetween;
    }

    public void setFoundBetween(int foundBetween) {
        this.foundBetween = foundBetween;
    }

    public int getFoundAfter() {
        return foundAfter;
    }

    public void setFoundAfter(int foundAfter) {
        this.foundAfter = foundAfter;
    }

    private int foundAfter;
    private String remarkAfter;

    public TaskDataEnvironment(int rowNo, String description, boolean checkBefore, int foundBefore, String remarkBefore, boolean checkBetween, int foundBetween, String remarkBetween, boolean checkAfter, int foundAfter, String remarkAfter) {
        this.rowNo = rowNo;
        this.description = description;
        this.checkBefore = checkBefore;
        this.foundBefore = foundBefore;
        this.remarkBefore = remarkBefore;
        this.checkBetween = checkBetween;
        this.foundBetween = foundBetween;
        this.remarkBetween = remarkBetween;
        this.checkAfter = checkAfter;
        this.foundAfter = foundAfter;
        this.remarkAfter = remarkAfter;
    }

    public TaskDataEnvironment() {
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

    public String getRemarkBefore() {
        return remarkBefore;
    }

    public void setRemarkBefore(String remarkBefore) {
        this.remarkBefore = remarkBefore;
    }

    public boolean isCheckBetween() {
        return checkBetween;
    }

    public void setCheckBetween(boolean checkBetween) {
        this.checkBetween = checkBetween;
    }

    public String getRemarkBetween() {
        return remarkBetween;
    }

    public void setRemarkBetween(String remarkBetween) {
        this.remarkBetween = remarkBetween;
    }

    public boolean isCheckAfter() {
        return checkAfter;
    }

    public void setCheckAfter(boolean checkAfter) {
        this.checkAfter = checkAfter;
    }

    public String getRemarkAfter() {
        return remarkAfter;
    }

    public void setRemarkAfter(String remarkAfter) {
        this.remarkAfter = remarkAfter;
    }
}
