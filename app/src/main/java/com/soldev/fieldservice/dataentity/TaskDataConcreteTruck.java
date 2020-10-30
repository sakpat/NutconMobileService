package com.soldev.fieldservice.dataentity;

import java.util.List;

public class TaskDataConcreteTruck {

    private int rowNo;
    private String licensePlate;
    private String timeStart;
    private String timeEnd;
    private double amount;
    private String remark;
    private List<ImageInfo> imageInfoList;

    public TaskDataConcreteTruck() {
    }

    public TaskDataConcreteTruck(int rowNo, String licensePlate, String timeStart, String timeEnd, double amount, String remark, List<ImageInfo> imageInfoList) {
        this.rowNo = rowNo;
        this.licensePlate = licensePlate;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.amount = amount;
        this.remark = remark;
        this.imageInfoList = imageInfoList;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ImageInfo> getImageInfoList() {
        return imageInfoList;
    }

    public void setImageInfoList(List<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }
}
