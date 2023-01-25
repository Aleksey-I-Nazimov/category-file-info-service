package org.numamo.category.file.info.service.controller.dto;

public final class FileProviderRqDto {

    private String parentFolderName;
    private String categoryCode;

    public String getParentFolderName() {
        return parentFolderName;
    }

    public void setParentFolderName(String parentFolderName) {
        this.parentFolderName = parentFolderName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Override
    public String toString() {
        return "FileProviderRqDto{" +
                "parentFolderName='" + parentFolderName + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                '}';
    }
}
