package com.soldev.fieldservice.dataentity;

public class ImageInfo {

    private int rowNumber;
    private String imageName;
    private String Description;

    public ImageInfo() {
    }

    public ImageInfo(int rowNumber, String imageName, String description) {
        this.rowNumber = rowNumber;
        this.imageName = imageName;
        Description = description;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
