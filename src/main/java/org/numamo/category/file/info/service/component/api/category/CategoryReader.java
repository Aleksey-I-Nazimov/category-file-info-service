package org.numamo.category.file.info.service.component.api.category;

import org.numamo.category.file.info.service.component.api.category.model.CategoryDmo;

import java.util.List;


public interface CategoryReader {

    List<CategoryDmo> read();

}
