package org.numamo.category.file.info.service.component.main.user;

import org.numamo.category.file.info.service.component.api.category.model.UserAccessDmo;
import org.numamo.category.file.info.service.component.api.main.mapper.UserRecordMapper;
import org.numamo.category.file.info.service.component.api.main.storage.additional.IdGenerator;
import org.numamo.category.file.info.service.component.api.main.user.UserComponent;
import org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo;
import org.numamo.category.file.info.service.repository.api.user.UserInfoRepository;
import org.numamo.category.file.info.service.repository.api.user.UserRecordRepository;
import org.numamo.category.file.info.service.repository.entity.user.UserInfoEntity;
import org.numamo.category.file.info.service.repository.entity.user.UserRecordEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;


@Component
public class UserComponentImpl implements UserComponent {

    private static final Logger LOGGER = getLogger(UserComponentImpl.class);

    private final IdGenerator idGenerator;
    private final UserRecordMapper userRecordMapper;
    private final UserRecordRepository userRecordRepository;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserComponentImpl(
            IdGenerator idGenerator,
            UserRecordMapper userRecordMapper,
            UserRecordRepository userRecordRepository,
            UserInfoRepository userInfoRepository
    ) {
        this.idGenerator = idGenerator;
        this.userRecordMapper = userRecordMapper;
        this.userRecordRepository = userRecordRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    @Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
    public UserRecordEntity getUserRecord(
            final String userLogin
    ) {
        final UserRecordEntity user = userRecordRepository
                .findByLogin(userLogin)
                .orElseThrow(() -> new IllegalArgumentException("The user was not found by login+" + userLogin));

        LOGGER.trace("The following user record was made: {}", user);
        return user;
    }

    @Override
    @Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
    public void checkAndInsertNewRecords(
            final List<UserAccessDmo> userAccessList
    ) {

        // TODO: Checking existence is not set!!!
        final List<UserRecordEntity> userRecords = userAccessList
                .stream()
                .map(u -> userRecordMapper.makeNewUserRecord(idGenerator.nextId(), u))
                .collect(toList());

        LOGGER.debug("The following user records: {} were made from {}", userRecords, userAccessList);

        userRecords.forEach(userRecordRepository::save);
    }

    @Override
    @Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
    public void insertUserRecord(UserDetailsDmo userDetails) {
        LOGGER.debug("Inserting the new user: {}", userDetails);

        final UserInfoEntity userInfo = userRecordMapper.makeUserInfo(idGenerator.nextId(), userDetails);
        final UserRecordEntity userRecord = userRecordMapper.makeUserRecord(idGenerator.nextId(), userDetails, userInfo);
        userInfo.setUserRecord(userRecord);

        userRecordRepository.save(userRecord);
        userInfoRepository.save(userInfo);

        LOGGER.trace("The new user info={} and record={} was added by {}", userRecord, userInfo, userDetails);
    }

}
