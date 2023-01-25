package org.numamo.category.file.info.service.controller.api;

import org.numamo.category.file.info.service.controller.dto.CategoryDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface CategoryApi {

    @GetMapping("/get/categories")
    List<CategoryDto> getCategories();

}
