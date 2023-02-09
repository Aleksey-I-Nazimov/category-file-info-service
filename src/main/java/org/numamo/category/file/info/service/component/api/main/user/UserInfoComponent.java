package org.numamo.category.file.info.service.component.api.main.user;

import org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo;

import java.util.Optional;

public interface UserInfoComponent {

    UserDetailsDmo loadUserByLogin(String login);

    Optional<UserDetailsDmo> loadUserByLoginOpt(String login);

}
