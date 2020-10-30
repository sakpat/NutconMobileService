package com.soldev.fieldservice.dataentity;

public class TaskDataExtraFee {
    private int rowNo;
    private String Desctipion;
    private double amount;
    private String dataType;
    private String Remark;

    public TaskDataExtraFee() {
    }

    public TaskDataExtraFee(int rowNo, String desctipion, double amount, String dataType, String remark) {
        this.rowNo = rowNo;
        Desctipion = desctipion;
        this.amount = amount;
        this.dataType = dataType;
        Remark = remark;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public String getDesctipion() {
        return Desctipion;
    }

    public void setDesctipion(String desctipion) {
        Desctipion = desctipion;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
