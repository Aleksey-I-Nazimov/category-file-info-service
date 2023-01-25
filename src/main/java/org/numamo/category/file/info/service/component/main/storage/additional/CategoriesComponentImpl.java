package org.numamo.category.file.info.service.component.main.storage.additional;

import org.numamo.category.file.info.service.component.api.category.model.CategoryDmo;
import org.numamo.category.file.info.service.component.api.main.mapper.CategoryMapper;
import org.numamo.category.file.info.service.component.api.main.storage.additional.CategoriesComponent;
import org.numamo.category.file.info.service.component.api.main.storage.additional.IdGenerator;
import org.numamo.category.file.info.service.repository.api.CategoryRepository;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public class CategoriesComponentImpl implements CategoriesComponent {

    private static final Logger LOGGER = getLogger(CategoriesComponentImpl.class);

    private final IdGenerator idGenerator;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoriesComponentImpl(
            IdGenerator idGenerator,
            CategoryMapper categoryMapper,
            CategoryRepository categoryRepository
    ) {
        this.idGenerator = idGenerator;
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public CategoryEntity save(final CategoryDmo category) {

        final CategoryEntity categoryEntity = categoryMapper.makeByDmo(category, idGenerator.nextId());
        categoryRepository.save(categoryEntity);

        LOGGER.trace("The following category {} was saved as {}",category,categoryEntity);
        return categoryEntity;
    }

}
