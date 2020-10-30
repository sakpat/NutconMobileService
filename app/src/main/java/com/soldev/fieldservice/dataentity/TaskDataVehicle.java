package com.soldev.fieldservice.dataentity;

public class TaskDataVehicle {
    private String licensePlate;
    private String assetNo;
    private long startMileage;
    private long endMileage;

    public TaskDataVehicle() {
    }

    public TaskDataVehicle(String licensePlate, String assetNo, long startMileage, long endMileage) {
        this.licensePlate = licensePlate;
        this.assetNo = assetNo;
        this.startMileage = startMileage;
        this.endMileage = endMileage;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public long getStartMileage() {
        return startMileage;
    }

    public void setStartMileage(long startMileage) {
        this.startMileage = startMileage;
    }

    public long getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(long endMileage) {
        this.endMileage = endMileage;
    }
}
