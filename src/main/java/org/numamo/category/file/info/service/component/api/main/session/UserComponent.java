package org.numamo.category.file.info.service.component.api.main.session;

import org.numamo.category.file.info.service.component.api.category.model.UserAccessDmo;
import org.numamo.category.file.info.service.repository.entity.UserRecordEntity;

import java.util.List;

public interface UserComponent {

    UserRecordEntity getUserRecord (String userLogin);

    void updateUsers(
            final List<UserAccessDmo> userAccessList
    );

}
