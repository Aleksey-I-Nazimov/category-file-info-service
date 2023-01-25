package org.numamo.category.file.info.service.controller.dto;

import java.util.List;


/**
 * The file controller model
 *
 * @author Nazimov Aleksey I.
 */
public final class FileDto {

    private String name;
    private String extensionCode;
    private Integer indexNumber;
    private List<String> folders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }

    public Integer getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(Integer indexNumber) {
        this.indexNumber = indexNumber;
    }

    public List<String> getFolders() {
        return folders;
    }

    public void setFolders(List<String> folders) {
        this.folders = folders;
    }

    @Override
    public String toString() {
        return "FileDto{" +
                "name='" + name + '\'' +
                ", extensionCode='" + extensionCode + '\'' +
                ", indexNumber=" + indexNumber +
                ", folders=" + folders +
                '}';
    }
}
