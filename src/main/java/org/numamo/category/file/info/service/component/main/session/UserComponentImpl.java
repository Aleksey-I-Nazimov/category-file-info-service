package org.numamo.category.file.info.service.component.main.session;

import org.numamo.category.file.info.service.component.api.category.model.UserAccessDmo;
import org.numamo.category.file.info.service.component.api.main.mapper.UserSessionMapper;
import org.numamo.category.file.info.service.component.api.main.storage.additional.IdGenerator;
import org.numamo.category.file.info.service.component.api.main.session.UserComponent;
import org.numamo.category.file.info.service.repository.api.UserRecordRepository;
import org.numamo.category.file.info.service.repository.entity.UserRecordEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;


@Component
public class UserComponentImpl implements UserComponent {

    private static final Logger LOGGER = getLogger(UserComponentImpl.class);

    private final IdGenerator idGenerator;
    private final UserSessionMapper userSessionMapper;
    private final UserRecordRepository userRecordRepository;

    @Autowired
    public UserComponentImpl(
            IdGenerator idGenerator,
            UserSessionMapper userSessionMapper,
            UserRecordRepository userRecordRepository
    ) {
        this.idGenerator = idGenerator;
        this.userSessionMapper = userSessionMapper;
        this.userRecordRepository = userRecordRepository;
    }

    @Override
    @Transactional(propagation = MANDATORY,isolation = READ_COMMITTED)
    public UserRecordEntity getUserRecord(final String userLogin) {

        final UserRecordEntity user = userRecordRepository
                .findByLogin(userLogin)
                .orElseGet(()->insertUser(userLogin));

        LOGGER.trace("The following user record was made: {}",user);
        return user;
    }

    @Override
    public void updateUsers(List<UserAccessDmo> userAccessList) {

    }

//    @Override
//    @Transactional(propagation = MANDATORY,isolation = READ_COMMITTED)
//    public void updateUsers(final List<String> userLoginList) {
//        userLoginList.forEach(this::insertUser);
//        LOGGER.trace("User list was persisted with in context");
//    }
//
//    @Override
//    @Transactional(propagation = MANDATORY,isolation = READ_COMMITTED)
//    public void updateUser(final String userLogin) {
//        insertUser(userLogin);
//        LOGGER.trace("User was persisted with in context");
//    }

    private UserRecordEntity insertUser (final String userLogin) {
        final UserRecordEntity userRecord = userSessionMapper
                .makeNewUserRecord(idGenerator.nextId(),requireNonNull(userLogin),"code");

        userRecordRepository.save(userRecord);
        LOGGER.debug("The following user record was created: {}",userRecord);
        return userRecord;
    }

}
