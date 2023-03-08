package org.numamo.category.file.info.service.component.main.user;

import org.numamo.category.file.info.service.component.api.main.mapper.UserRecordMapper;
import org.numamo.category.file.info.service.component.api.main.user.UserInfoComponent;
import org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo;
import org.numamo.category.file.info.service.repository.api.user.UserRecordRepository;
import org.numamo.category.file.info.service.repository.entity.user.UserRecordEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;


@Service
public class UserInfoComponentImpl implements UserInfoComponent {

    private static final Logger LOGGER = getLogger(UserInfoComponentImpl.class);

    private final UserRecordRepository userRecordRepository;
    private final UserRecordMapper userRecordMapper;

    @Autowired
    public UserInfoComponentImpl(
            UserRecordRepository userRecordRepository,
            UserRecordMapper userRecordMapper
    ) {
        this.userRecordRepository = userRecordRepository;
        this.userRecordMapper = userRecordMapper;
    }

    @Override
    @Transactional
    public UserDetailsDmo loadUserByLogin(String login) {

        final UserRecordEntity userRecord = userRecordRepository
                .findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException(login));

        final UserDetailsDmo userDetails = userRecordMapper.makeUserDetails(userRecord);

        LOGGER.debug("Got user {} from {}", userDetails, userRecord);
        return userDetails;
    }

    @Override
    public Optional<UserDetailsDmo> loadUserByLoginOpt(
            final String login
    ) {
        return userRecordRepository
                .findByLogin(login)
                .map(userRecordMapper::makeUserDetails);
    }
}
