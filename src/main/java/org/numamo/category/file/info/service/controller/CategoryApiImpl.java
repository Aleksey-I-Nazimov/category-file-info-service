package org.numamo.category.file.info.service.controller;


import org.numamo.category.file.info.service.component.api.main.provider.CategoryProvider;
import org.numamo.category.file.info.service.controller.api.CategoryApi;
import org.numamo.category.file.info.service.controller.dto.CategoryDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class CategoryApiImpl implements CategoryApi {

    private static final Logger LOGGER = getLogger(CategoryApiImpl.class);

    private final CategoryProvider categoryProvider;

    @Autowired
    public CategoryApiImpl(CategoryProvider categoryProvider) {
        this.categoryProvider = categoryProvider;
    }

    @Override
    public List<CategoryDto> getCategories() {

        LOGGER.info("Requesting category list");

        final List<CategoryDto> categories = categoryProvider.readAll();

        LOGGER.info("The following category list was read: {}",categories);
        return categories;
    }
}
