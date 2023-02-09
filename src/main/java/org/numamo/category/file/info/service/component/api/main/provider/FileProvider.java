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
     * @param indexNumber is the current index number
     * @return the list of read root folders
     */
    List<FolderDto> findRootFolders(String categoryCode, Long indexNumber);


    /**
     * The method of reading folders by tree coordinates
     * @param parentKey is the primary KEY of the parent folder
     * @param indexNumber is the current index number
     * @return the list of folders
     */
    List<FolderDto> findFolders(Long parentKey, Long indexNumber);


    /**
     * The method of reading files by the parent and category
     * @param parentKey is the primary key parent folder
     * @param indexNumber is the current index number
     * @return the list of files
     */
    List<FileDto> findFiles(Long parentKey, Long indexNumber);


}
