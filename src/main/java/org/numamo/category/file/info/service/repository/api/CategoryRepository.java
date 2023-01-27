package org.numamo.category.file.info.service.repository.api;

import org.numamo.category.file.info.service.repository.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByCode(String code);

}
