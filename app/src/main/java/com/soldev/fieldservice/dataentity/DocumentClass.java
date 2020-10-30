package com.soldev.fieldservice.dataentity;

public class DocumentClass {
    private String applicationNo;
    private String documentCategory;
    private String documentName;
    private String documentExtention;
    private String documentContent;

    public DocumentClass() {
    }

    public DocumentClass(String applicationNo, String documentCategory, String documentName, String documentExtention, String documentContent) {
        this.applicationNo = applicationNo;
        this.documentCategory = documentCategory;
        this.documentName = documentName;
        this.documentExtention = documentExtention;
        this.documentContent = documentContent;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getDocumentCategory() {
        return documentCategory;
    }

    public void setDocumentCategory(String documentCategory) {
        this.documentCategory = documentCategory;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentExtention() {
        return documentExtention;
    }

    public void setDocumentExtention(String documentExtention) {
        this.documentExtention = documentExtention;
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }
}
