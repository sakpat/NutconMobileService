package com.soldev.fieldservice.dataentity;

public class Personal {
    private String userName;
    private String prefix;
    private String firstName;
    private String lastName;
    private String mobilePhoneNo;
    private String userRole;
    private boolean inActivated;
    private String getUserName;

    public Personal() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobilePhoneNo() {
        return mobilePhoneNo;
    }

    public void setMobilePhoneNo(String mobilePhoneNo) {
        this.mobilePhoneNo = mobilePhoneNo;
    }

    public boolean isInActivated() {
        return inActivated;
    }

    public void setInActivated(boolean inActivated) {
        this.inActivated = inActivated;
    }

    public String getGetUserName() {
        return getUserName;
    }

    public void setGetUserName(String getUserName) {
        this.getUserName = getUserName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
