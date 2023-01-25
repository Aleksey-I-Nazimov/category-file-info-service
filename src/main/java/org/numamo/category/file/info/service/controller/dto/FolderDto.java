package org.numamo.category.file.info.service.controller.dto;


/**
 * The root folder of the selected category
 *
 * @author Nazimov Aleksey I.
 */
public final class FolderDto {

    private String name;
    private Integer indexNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(Integer indexNumber) {
        this.indexNumber = indexNumber;
    }

    @Override
    public String toString() {
        return "FolderDto{" +
                "name='" + name + '\'' +
                ", indexNumber=" + indexNumber +
                '}';
    }
}
