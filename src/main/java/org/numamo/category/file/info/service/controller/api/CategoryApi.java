package org.numamo.category.file.info.service.controller.api;

import org.numamo.category.file.info.service.controller.dto.CategoryDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.numamo.category.file.info.service.config.SecurityConfig.USER_API_PREFIX;

public interface CategoryApi {

    @GetMapping(USER_API_PREFIX + "/get/categories")
    List<CategoryDto> getCategories();

}
