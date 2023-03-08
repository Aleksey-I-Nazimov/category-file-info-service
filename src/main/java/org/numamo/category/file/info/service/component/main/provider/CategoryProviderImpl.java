package org.numamo.category.file.info.service.component.main.provider;

import org.numamo.category.file.info.service.component.api.main.mapper.CategoryMapper;
import org.numamo.category.file.info.service.component.api.main.provider.CategoryProvider;
import org.numamo.category.file.info.service.controller.dto.CategoryDto;
import org.numamo.category.file.info.service.repository.api.CategoryRepository;
import org.numamo.category.file.info.service.repository.api.index.FileSysIndexEditor;
import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;


@Component
@Transactional
public class CategoryProviderImpl implements CategoryProvider {

    private static final Logger LOGGER = getLogger(CategoryProviderImpl.class);

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileSysIndexEditor fileSysIndexEditor;

    @Autowired
    public CategoryProviderImpl(
            CategoryRepository categoryRepository,
            CategoryMapper categoryMapper,
            FileSysIndexEditor fileSysIndexEditor
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.fileSysIndexEditor = fileSysIndexEditor;
    }


    @Override
    public List<CategoryDto> readAll() {

        LOGGER.debug("Requesting category list...");

        final List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryRepository.findAll().forEach(categoryEntities::add);

        final FileSysIndexEntity fileSysIndex = fileSysIndexEditor.findAppliedActualIndex()
                .orElseThrow(() -> new IllegalStateException("The actual index is not defined"));

        final List<CategoryDto> categoryDtoList = categoryEntities
                .stream()
                .map(e -> categoryMapper.make(e, fileSysIndex))
                .collect(toList());

        LOGGER.debug("Read category list: {}", categoryDtoList);
        return categoryDtoList;
    }

}
