package org.numamo.category.file.info.service.component.api.main.user;

import org.numamo.category.file.info.service.component.api.category.model.UserAccessDmo;
import org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo;
import org.numamo.category.file.info.service.repository.entity.user.UserRecordEntity;

import java.util.List;

public interface UserComponent {

    UserRecordEntity getUserRecord(String userLogin);

    void checkAndInsertNewRecords(
            final List<UserAccessDmo> userAccessList
    );

    void insertUserRecord(UserDetailsDmo userDetailsDmo);

}
