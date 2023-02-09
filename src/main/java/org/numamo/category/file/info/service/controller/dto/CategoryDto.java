package org.numamo.category.file.info.service.controller.dto;


/**
 * The model of file category
 *
 * @author Nazimov Aleksey I.
 */
public final class CategoryDto {

    private String code;
    private String name;
    private String indexNumber;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", indexNumber=" + indexNumber +
                '}';
    }

}
