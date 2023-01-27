package org.numamo.category.file.info.service.component.api.main.storage.additional;

import org.numamo.category.file.info.service.component.api.category.model.CategoryDmo;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;

public interface CategoriesComponent {

    CategoryEntity save(CategoryDmo category);

}
