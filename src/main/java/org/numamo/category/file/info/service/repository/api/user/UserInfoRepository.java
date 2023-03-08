package org.numamo.category.file.info.service.repository.api.user;

import org.numamo.category.file.info.service.repository.entity.user.UserInfoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfoEntity, Long> {

}
