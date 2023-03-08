package org.numamo.category.file.info.service.component.api.main.provider;

import org.numamo.category.file.info.service.controller.dto.CategoryDto;

import java.util.List;


/**
 * The service of category provider
 *
 * @author Nazimov Aleksey I.
 */
public interface CategoryProvider {

    /**
     * The method reads all available categories
     *
     * @return the list categories
     */
    List<CategoryDto> readAll();

}
