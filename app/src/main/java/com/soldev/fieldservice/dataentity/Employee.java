package com.soldev.fieldservice.dataentity;

public class Employee {
    private int rowNumber;
    private String empID;
    private String empName;
    private String timeStart;
    private String timeEnd;
    private String absent;
    private String wellDress;
    private String wellBehavior;
    private String Remark;
    private String dataType;

    public Employee() {
    }

    public Employee(int rowNumber, String empID, String empName, String timeStart, String timeEnd, String wellDress, String wellBehavior, String remark, String dataType) {
        this.rowNumber = rowNumber;
        this.empID = empID;
        this.empName = empName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.wellDress = wellDress;
        this.wellBehavior = wellBehavior;
        Remark = remark;
        this.dataType = dataType;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public String getWellDress() {
        if (wellDress==null){
            return "";
        }
        return wellDress;
    }

    public void setWellDress(String wellDress) {
        this.wellDress = wellDress;
    }

    public String getWellBehavior() {
        if(wellBehavior==null){
            return "";
        }
        return wellBehavior;
    }

    public void setWellBehavior(String wellBehavior) {
        this.wellBehavior = wellBehavior;
    }

    public String getRemark() {
        if(Remark==null){
            return "";
        }
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }
}
