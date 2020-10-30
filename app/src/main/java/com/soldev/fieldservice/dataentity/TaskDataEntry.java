package com.soldev.fieldservice.dataentity;

import android.util.Log;

import com.soldev.fieldservice.utilities.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class TaskDataEntry {
    private String taskID;
    private String taskNo;
    private String jobNo;
    private String taskDate;
    private String compareTaskDate;
    private String taskTypeCode;
    private String taskType;
    private String taskTypeCheckList;
    private String taskGroupCode;
    private String taskGroup;
    private String customerName;
    private String contactName;
    private String contactNo;
    private String saleName;
    private String saleNumber;
    private String projectName;
    private String location;
    private String taskStatus;
    private String taskFlowGroup;
    private double longitude;
    private double latitude;
    private List<TaskDataMachine> machineList;
    private List<TaskDataEquipmentList> equipmentList;
    private List<TaskDataEnvironmentList> environmentCheckLists;
    private List<TaskDataWorkingList> workingSiteCheckList;
    private List<TaskDataConcreteTruck> taskDataConcreteTrucks;
    private List<TaskDataExtraFee> extraFeeList;
    private String taskCreator;
    private String taskCreatedDate;
    private String taskAssignedDate;
    private String taskPerformer;
    private String performerName;
    private String readDate;
    private String docRef;
    private String taskRemark;
    private String createRemark;
    private String performedDate;
    private String submittedToReiviewDate;
    private String taskReviewer;
    private String reviewedDate;
    private String adminRemark;
    private TaskDataVehicle taskDataVehicle;
    //------------- Performing ---------------//
    private String concreteType;
    private double checkinLongitude;
    private double checkinLatitude;
    private String checkInTime;
    private String checkInSignImage;
    private String drawingImage;
    private String pipeImage;
    private long pipeLength;
    private String pipeRemark;
    private double minimumCementVolumn;
    private double totalCementVolumn;
    //------ Employee
    private List<Employee> employees;
    private List<ImageInfo> imageInfos;
    //------ delivery
    private boolean hasReceiver;
    private String deliverDate;
    private String taskReceiver;
    private String taskReceiverContact;
    private String teamBehavior;
    private String teamPerformQaulity;
    private String deliverSignature;
    private String receiveSignature;
    private String taskDeliver;
    private String taskDocRemark;
    private boolean lockSurvey ;



    //Polish perform
    private String productType;
    private double areaByPo;
    private String areaByOrder;
    private double areaActual;
    private String areaPerform;
    private String polishRemark;

    //QC
    private String workerQC;
    private String qcStatus;
    private String qcBeforeTime;
    private String qcBetweenTime;
    private String qcAfterTime;
    private List<TaskDataEnvironmentList> qcEnvironmentCheckLists;
    private List<TaskDataWorkingList> qcWorkingSiteCheckList;

    public List<ImageInfo> getImageInfosQC() {
        return imageInfosQC;
    }

    public void setImageInfosQC(List<ImageInfo> imageInfosQC) {
        this.imageInfosQC = imageInfosQC;
    }

    private List<ImageInfo> imageInfosQC;

    //for android only
    private String taskScreenType;


    private boolean isWorkPerformed = true;
    private boolean alreadyPrinted = false;

    private String lastUpDateBy;
    private String lastUpDateDate;




    public String getDeliveryStatus() {
        if(deliveryStatus==null){
            return AppConstant.TASK_STATUS_NEW;
        }
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    private String deliveryStatus;




    public TaskDataEntry(){

    }


    public TaskDataEntry(String taskID, String taskNo, String taskDate, String taskTypeCode, String taskType, String taskGroupCode, String taskGroup, String customerName, String contactNo, String location, String taskStatus, double longitude, double latitude, List<TaskDataMachine> machineList, List<TaskDataEquipmentList> equipmentList, List<TaskDataEnvironmentList> environmentCheckLists, List<TaskDataWorkingList> workingSiteCheckList, String taskCreator, String taskPerformer, String performedDate, TaskDataVehicle taskDataVehicle) {
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
        this.longitude = longitude;
        this.latitude = latitude;
        this.machineList = machineList;
        this.equipmentList = equipmentList;
        this.environmentCheckLists = environmentCheckLists;
        this.workingSiteCheckList = workingSiteCheckList;
        this.taskCreator = taskCreator;
        this.taskPerformer = taskPerformer;
        this.performedDate = performedDate;
        this.taskDataVehicle = taskDataVehicle;

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

    public String getTaskID() {
        return taskID;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getCompareTaskDate() {
        if(compareTaskDate==null){
            compareTaskDate = taskDate.substring(6)+taskDate.substring(3,5)+taskDate.substring(0,2);
            return "";
        }
        return compareTaskDate;
    }

    public void setCompareTaskDate(String compareTaskDate) {
        this.compareTaskDate = compareTaskDate;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getLocation() {
        return location;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public List<TaskDataMachine> getMachineList() {
        return machineList;
    }

    public List<TaskDataEquipmentList> getEquipmentList() {
        return equipmentList;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setMachineList(List<TaskDataMachine> machineList) {
        this.machineList = machineList;
    }

    public void setEquipmentList(List<TaskDataEquipmentList> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
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
    public String getTaskTypeCode() {
        return taskTypeCode;
    }

    public void setTaskTypeCode(String taskTypeCode) {
        this.taskTypeCode = taskTypeCode;
    }

    public TaskDataVehicle getTaskDataVehicle() {
        return taskDataVehicle;
    }

    public void setTaskDataVehicle(TaskDataVehicle taskDataVehicle) {
        this.taskDataVehicle = taskDataVehicle;
    }

    public List<TaskDataEnvironmentList> getEnvironmentCheckLists() {
        return environmentCheckLists;
    }

    public void setEnvironmentCheckLists(List<TaskDataEnvironmentList> environmentCheckLists) {
        this.environmentCheckLists = environmentCheckLists;
    }

    public List<TaskDataWorkingList> getWorkingSiteCheckList() {
        return workingSiteCheckList;
    }

    public void setWorkingSiteCheckList(List<TaskDataWorkingList> workingSiteCheckList) {
        this.workingSiteCheckList = workingSiteCheckList;
    }

    public String getConcreteType() {
        return concreteType;
    }

    public void setConcreteType(String concreteType) {
        this.concreteType = concreteType;
    }

    public double getCheckinLongitude() {
        return checkinLongitude;
    }

    public void setCheckinLongitude(double checkinLongitude) {
        this.checkinLongitude = checkinLongitude;
    }

    public double getCheckinLatitude() {
        return checkinLatitude;
    }

    public void setCheckinLatitude(double checkinLatitude) {
        this.checkinLatitude = checkinLatitude;
    }

    public long getPipeLength() {
        return pipeLength;
    }

    public void setPipeLength(long pipeLength) {
        this.pipeLength = pipeLength;
    }

    public double getTotalCementVolumn() {
        return totalCementVolumn;
    }

    public void setTotalCementVolumn(double totalCementVolumn) {
        this.totalCementVolumn = totalCementVolumn;
    }

    public List<TaskDataConcreteTruck> getTaskDataConcreteTrucks() {
        return taskDataConcreteTrucks;
    }

    public void setTaskDataConcreteTrucks(List<TaskDataConcreteTruck> taskDataConcreteTrucks) {
        this.taskDataConcreteTrucks = taskDataConcreteTrucks;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee e){
        employees.add(e);
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void instantiateEmployees() {
        this.employees  = new ArrayList<>();
    }

    public List<ImageInfo> getImageInfos() {
        return imageInfos;
    }

    public void setImageInfos(List<ImageInfo> imageInfos) {
        this.imageInfos = imageInfos;
    }

    public List<TaskDataExtraFee> getExtraFeeList() {
        return extraFeeList;
    }

    public void setExtraFeeList(List<TaskDataExtraFee> extraFeeList) {
        this.extraFeeList = extraFeeList;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckInSignImage() {
        return checkInSignImage;
    }

    public void setCheckInSignImage(String checkInSignImage) {
        this.checkInSignImage = checkInSignImage;
    }

    public String getDrawingImage() {
        return drawingImage;
    }

    public void setDrawingImage(String drawingImage) {
        this.drawingImage = drawingImage;
    }

    public String getPipeImage() {
        return pipeImage;
    }

    public void setPipeImage(String pipeImage) {
        this.pipeImage = pipeImage;
    }

    public String getPipeRemark() {
        return pipeRemark;
    }

    public void setPipeRemark(String pipeRemark) {
        this.pipeRemark = pipeRemark;
    }

    public boolean isHasReceiver() {
        return hasReceiver;
    }

    public void setHasReceiver(boolean hasReceiver) {
        this.hasReceiver = hasReceiver;
    }

    public String getTaskReceiver() {
        return taskReceiver;
    }

    public void setTaskReceiver(String taskReceiver) {
        this.taskReceiver = taskReceiver;
    }

    public String getTaskReceiverContact() {
        return taskReceiverContact;
    }

    public void setTaskReceiverContact(String taskReceiverContact) {
        this.taskReceiverContact = taskReceiverContact;
    }

    public String getTeamBehavior() {
        return teamBehavior;
    }

    public void setTeamBehavior(String teamBehavior) {
        this.teamBehavior = teamBehavior;
    }

    public String getTeamPerformQaulity() {
        return teamPerformQaulity;
    }

    public void setTeamPerformQaulity(String teamPerformQaulity) {
        this.teamPerformQaulity = teamPerformQaulity;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }



    public String getPolishRemark() {
        return polishRemark;
    }

    public void setPolishRemark(String polishRemark) {
        this.polishRemark = polishRemark;
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

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }

    public String getSubmittedToReiviewDate() {
        return submittedToReiviewDate;
    }

    public void setSubmittedToReiviewDate(String submittedToReiviewDate) {
        this.submittedToReiviewDate = submittedToReiviewDate;
    }

    public String getTaskReviewer() {
        return taskReviewer;
    }

    public void setTaskReviewer(String taskReviewer) {
        this.taskReviewer = taskReviewer;
    }

    public String getReviewedDate() {
        return reviewedDate;
    }

    public void setReviewedDate(String reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    public double getMinimumCementVolumn() {
        return minimumCementVolumn;
    }

    public void setMinimumCementVolumn(double minimumCementVolumn) {
        this.minimumCementVolumn = minimumCementVolumn;
    }

    public String getTaskFlowGroup() {
        return taskFlowGroup;
    }

    public void setTaskFlowGroup(String taskFlowGroup) {
        this.taskFlowGroup = taskFlowGroup;
    }

    public String getQcStatus() {
        if(qcStatus==null){
            return AppConstant.TASK_STATUS_NEW;
        }
        return qcStatus;
    }

    public void setQcStatus(String qcStatus) {
        this.qcStatus = qcStatus;
    }

    public List<TaskDataEnvironmentList> getQcEnvironmentCheckLists() {
        return qcEnvironmentCheckLists;
    }

    public void setQcEnvironmentCheckLists(List<TaskDataEnvironmentList> qcEnvironmentCheckLists) {
        this.qcEnvironmentCheckLists = qcEnvironmentCheckLists;
    }

    public List<TaskDataWorkingList> getQcWorkingSiteCheckList() {
        return qcWorkingSiteCheckList;
    }

    public void setQcWorkingSiteCheckList(List<TaskDataWorkingList> qcWorkingSiteCheckList) {
        this.qcWorkingSiteCheckList = qcWorkingSiteCheckList;
    }

    public String getQcBeforeTime() {
        return qcBeforeTime;
    }

//    public void setQcBeforeTime(String qcBeforeTime) {
//        Log.e("checkqc","qcBeforeTime1>"+qcBeforeTime);
//        this.qcBeforeTime = qcBeforeTime;
//    }

    public void setQcBefTime(String string) {
        Log.e("checkqc","qcBeforeTime2>"+qcBeforeTime);
        this.qcBeforeTime = string;
    }

    public String getQcBetweenTime() {
        return qcBetweenTime;
    }

    public void setQcBetweenTime(String qcBetweenTime) {
        this.qcBetweenTime = qcBetweenTime;
    }

    public String getQcAfterTime() {
        return qcAfterTime;
    }

    public void setQcAfterTime(String qcAfterTime) {
        this.qcAfterTime = qcAfterTime;
    }

    public String getDeliverSignature() {
        return deliverSignature;
    }

    public void setDeliverSignature(String deliverSignature) {
        this.deliverSignature = deliverSignature;
    }

    public String getReceiveSignature() {
        return receiveSignature;
    }

    public void setReceiveSignature(String receiveSignature) {
        this.receiveSignature = receiveSignature;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }

    public String getTaskRemark() {
        if(taskRemark==null){
            taskRemark = "";
        }
        return taskRemark;
    }
    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    public String getContactName() {
        if(contactName==null){
            return "";
        }
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public String getTaskDocRemark() {
        if(taskDocRemark==null){
            return "";
        }
        return taskDocRemark;
    }

    public void setTaskDocRemark(String taskDocRemark) {
        this.taskDocRemark = taskDocRemark;
    }

    public String getTaskDeliver() {
        if(taskDeliver==null){
            return "";
        }
        return taskDeliver;
    }

    public void setTaskDeliver(String taskDeliver) {
        this.taskDeliver = taskDeliver;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getJobNo() {
        if(jobNo==null){
            return "";
        }
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(String saleNumber) {
        this.saleNumber = saleNumber;
    }

    public boolean isLockSurvey() {
        return lockSurvey;
    }

    public void setLockSurvey(boolean lockSurvey) {
        this.lockSurvey = lockSurvey;
    }

    public String getTaskScreenType() {
        return taskScreenType;
    }

    public void setTaskScreenType(String taskScreenType) {
        this.taskScreenType = taskScreenType;
    }

    public String getWorkerQC() {
        if(workerQC==null){
            return  "xx";
        }
        return workerQC;
    }

    public void setWorkerQC(String workerQC) {
        this.workerQC = workerQC;
    }

    public String getTaskTypeCheckList() {
        return taskTypeCheckList;
    }

    public void setTaskTypeCheckList(String taskTypeCheckListว) {
        this.taskTypeCheckList = taskTypeCheckListว;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public String getAreaByOrder() {
        if(areaByOrder==null){
            areaByOrder ="";
        }
        return areaByOrder;
    }

    public void setAreaByOrder(String areaByOrder) {
        this.areaByOrder = areaByOrder;
    }

    public String getCreateRemark() {
        return createRemark;
    }

    public void setCreateRemark(String createRemark) {
        this.createRemark = createRemark;
    }

    public String getAreaPerform() {
        if(areaPerform==null){
            areaPerform = "";
        }
        return areaPerform;
    }

    public void setAreaPerform(String areaPerform) {
        this.areaPerform = areaPerform;
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    public boolean isWorkPerformed() {

        return isWorkPerformed;
    }

    public void setWorkPerformed(boolean workPerformed) {
        isWorkPerformed = workPerformed;
    }

    public boolean isAlreadyPrinted() {
        return alreadyPrinted;
    }

    public void setAlreadyPrinted(boolean alreadyPrinted) {
        this.alreadyPrinted = alreadyPrinted;
    }

    public String getLastUpDateBy() {
        return lastUpDateBy;
    }

    public void setLastUpDateBy(String lastUpDateBy) {
        this.lastUpDateBy = lastUpDateBy;
    }

    public String getLastUpDateDate() {
        return lastUpDateDate;
    }

    public void setLastUpDateDate(String lastUpDateDate) {
        this.lastUpDateDate = lastUpDateDate;
    }
}

