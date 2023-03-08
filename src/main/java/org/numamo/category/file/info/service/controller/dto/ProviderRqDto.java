package org.numamo.category.file.info.service.controller.dto;


public final class ProviderRqDto {

    private String parentKey;
    private String parentIndexNumber;
    private String categoryCode;

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getParentIndexNumber() {
        return parentIndexNumber;
    }

    public void setParentIndexNumber(String parentIndexNumber) {
        this.parentIndexNumber = parentIndexNumber;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Override
    public String toString() {
        return "ProviderRqDto{" +
                "parentKey=" + parentKey +
                ", parentIndexNumber=" + parentIndexNumber +
                ", categoryCode='" + categoryCode + '\'' +
                '}';
    }

}
