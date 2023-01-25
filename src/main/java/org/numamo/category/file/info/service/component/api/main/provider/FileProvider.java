package org.numamo.category.file.info.service.component.api.main.provider;

import org.numamo.category.file.info.service.controller.dto.FileDto;
import org.numamo.category.file.info.service.controller.dto.FolderDto;

import java.util.List;


/**
 * The service of category provider
 *
 * @author Nazimov Aleksey I.
 */
public interface FileProvider {

    /**
     * The method reads root directories by the category code
     * @param categoryCode is the string category code
     * @return the list of read root folders
     */
    List<FolderDto> findRootFolders(String categoryCode);


    /**
     * The method of reading folders by tree coordinates
     * @param parentFolderName is the name of root folder
     * @param categoryCode is the category code
     * @return the list of folders
     */
    List<FolderDto> findFolders (String parentFolderName, String categoryCode);


    /**
     * The method reads root files by the category code
     * @param categoryCode is the category code
     * @return the list of read root files
     */
    List<FileDto> findRootFiles (String categoryCode);


    /**
     * The method of reading files by the parent and category
     * @param parentFolderName is the parent folder
     * @param categoryCode is the category code
     * @return the list of files
     */
    List<FileDto> findFiles (String parentFolderName, String categoryCode);


}
