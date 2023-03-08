package org.numamo.category.file.info.service.controller.dto;


/**
 * The root folder of the selected category
 *
 * @author Nazimov Aleksey I.
 */
public final class FolderDto {

    private String key;
    private String name;
    private String indexNumber;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
        return "FolderDto{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", indexNumber=" + indexNumber +
                '}';
    }
}
