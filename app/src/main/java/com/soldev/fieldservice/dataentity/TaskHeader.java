package com.soldev.fieldservice.dataentity;

public class TaskHeader {
    private String taskID;
    private String taskNo;
    private String taskDate;
    private String taskTypeCode;
    private String taskType;
    private String taskGroupCode;
    private String taskGroup;
    private String customerName;
    private String contactNo;
    private String location;
    private double longitude;
    private double latitude;
    private String taskStatus;
    private String taskFlowGroup;
    private String taskCreator;
    private String taskCreatedDate;
    private String taskAssignedDate;
    private String taskPerformer;
    private String performedDate;
    private double areaByPo;
    private String docRef;
    private String taskRemark;
    private double minimumCementVolumn;

    public TaskHeader(){

    }


    public String getTaskCreatedDate() {
        return taskCreatedDate;
    }

    public void setTaskCreatedDate(String taskCreatedDate) {
        this.taskCreatedDate = taskCreatedDate;
    }

    public String getTaskAssignedDate() {
        return taskAssignedDate;
    }

    public void setTaskAssignedDate(String taskAssignedDate) {
        this.taskAssignedDate = taskAssignedDate;
    }

    public double getAreaByPo() {
        return areaByPo;
    }

    public void setAreaByPo(double areaByPo) {
        this.areaByPo = areaByPo;
    }

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    public double getMinimumCementVolumn() {
        return minimumCementVolumn;
    }

    public void setMinimumCementVolumn(double minimumCementVolumn) {
        this.minimumCementVolumn = minimumCementVolumn;
    }

    public TaskHeader(String taskID, String taskNo, String taskDate, String performedDate , String taskTypeCode, String taskType, String taskGroupCode, String taskGroup
            , String customerName, String contactNo, String location, String taskStatus,
                      String taskCreator, String taskPerformer, double longitude, double latitude, String taskFlowGroup) {
        this.taskID = taskID;
        this.taskNo = taskNo;
        this.taskDate = taskDate;
        this.taskTypeCode = taskTypeCode;
        this.taskType = taskType;
        this.taskGroupCode = taskGroupCode;
        this.taskGroup = taskGroup;
        this.customerName = customerName;
        this.contactNo = contactNo;
        this.location = location;
        this.taskStatus = taskStatus;
        this.taskCreator = taskCreator;
        this.taskPerformer = taskPerformer;
        this.performedDate = performedDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.taskFlowGroup = taskFlowGroup;
    }

    public String getTaskTypeCode() {
        return taskTypeCode;
    }

    public void setTaskTypeCode(String taskTypeCode) {
        this.taskTypeCode = taskTypeCode;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskCreator() {
        return taskCreator;
    }

    public void setTaskCreator(String taskCreator) {
        this.taskCreator = taskCreator;
    }

    public String getTaskPerformer() {
        return taskPerformer;
    }

    public void setTaskPerformer(String taskPerformer) {
        this.taskPerformer = taskPerformer;
    }


    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public String getTaskGroupCode() {
        return taskGroupCode;
    }

    public void setTaskGroupCode(String taskGroupCode) {
        this.taskGroupCode = taskGroupCode;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public String getPerformedDate() {
        return performedDate;
    }

    public void setPerformedDate(String performedDate) {
        this.performedDate = performedDate;
    }

    public String getTaskFlowGroup() {
        return taskFlowGroup;
    }

    public void setTaskFlowGroup(String taskFlowGroup) {
        this.taskFlowGroup = taskFlowGroup;
    }


}
