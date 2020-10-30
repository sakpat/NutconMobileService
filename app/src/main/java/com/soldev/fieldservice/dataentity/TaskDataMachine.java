package com.soldev.fieldservice.dataentity;

public class TaskDataMachine {
    private int rowNo;
    private String Code;
    private String Name;
    private String Remark;

    public TaskDataMachine(int rowNo, String code, String name, String remark) {
        this.rowNo = rowNo;
        Code = code;
        Name = name;
        Remark = remark;
    }

    public TaskDataMachine() {
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
