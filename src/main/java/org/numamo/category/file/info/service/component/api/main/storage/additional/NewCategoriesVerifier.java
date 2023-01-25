package org.numamo.category.file.info.service.component.api.main.storage.additional;

import org.numamo.category.file.info.service.component.api.category.model.CategoryDmo;

import java.util.List;


public interface NewCategoriesVerifier {

    void check (List<CategoryDmo> categories);

}
