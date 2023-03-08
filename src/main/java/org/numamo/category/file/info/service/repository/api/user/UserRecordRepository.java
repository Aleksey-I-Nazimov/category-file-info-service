package org.numamo.category.file.info.service.repository.api.user;

import org.numamo.category.file.info.service.repository.entity.user.UserRecordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRecordRepository extends CrudRepository<UserRecordEntity, Long> {

    Optional<UserRecordEntity> findByLogin(String login);

}
