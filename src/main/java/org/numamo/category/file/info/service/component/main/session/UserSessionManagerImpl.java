package org.numamo.category.file.info.service.component.main.session;

import org.numamo.category.file.info.service.component.api.main.mapper.UserSessionMapper;
import org.numamo.category.file.info.service.component.api.main.session.UserComponent;
import org.numamo.category.file.info.service.component.api.main.session.UserSessionManager;
import org.numamo.category.file.info.service.component.api.main.storage.additional.IdGenerator;
import org.numamo.category.file.info.service.config.api.AppConfig;
import org.numamo.category.file.info.service.repository.api.FileSessionRepository;
import org.numamo.category.file.info.service.repository.api.index.FileSysIndexEditor;
import org.numamo.category.file.info.service.repository.entity.FileSessionEntity;
import org.numamo.category.file.info.service.repository.entity.UserRecordEntity;
import org.numamo.category.file.info.service.repository.entity.index.FileSysIndexEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;
import static org.springframework.transaction.annotation.Propagation.NESTED;


@Component
public class UserSessionManagerImpl implements UserSessionManager {


    // Variables and constructors:-------------------------------------------------------
    private static final Logger LOGGER = getLogger(UserSessionManagerImpl.class);

    private final FileSessionRepository fileSessionRepository;
    private final FileSysIndexEditor fileSysIndexEditor;
    private final UserSessionMapper userSessionMapper;
    private final UserComponent userComponent;
    private final IdGenerator idGenerator;
    private final AppConfig appConfig;

    @Autowired
    public UserSessionManagerImpl(
            FileSessionRepository fileSessionRepository,
            FileSysIndexEditor fileSysIndexEditor,
            UserSessionMapper userSessionMapper,
            UserComponent userComponent,
            IdGenerator idGenerator,
            AppConfig appConfig
    ) {
        this.fileSessionRepository = fileSessionRepository;
        this.fileSysIndexEditor = fileSysIndexEditor;
        this.userSessionMapper = userSessionMapper;
        this.userComponent = userComponent;
        this.idGenerator = idGenerator;
        this.appConfig = appConfig;
    }


    // Public API:-----------------------------------------------------------------------
    @Override
    @Transactional(propagation = MANDATORY,isolation = READ_COMMITTED)
    public long createNewSession(final String userLogin) {

        LOGGER.info("Creating new session with user login = {}", userLogin);

        final UserRecordEntity user = userComponent.getUserRecord(userLogin);

        final FileSysIndexEntity actualIndex = fileSysIndexEditor.findAppliedActualIndex()
                .orElseThrow(() -> new IllegalStateException("The actual index was not created"));

        final FileSessionEntity fileSessionEntity = userSessionMapper
                .makeNewFileSessionEntity(idGenerator.nextId(), user, actualIndex);
        fileSessionRepository.save(fileSessionEntity);

        LOGGER.debug("The new session was created: {}", fileSessionEntity);
        return fileSessionEntity.getId();
    }

    @Override
    @Transactional(propagation = MANDATORY,isolation = READ_COMMITTED)
    public boolean updateUserSession(long sessionId) {

        LOGGER.info("Updating user session was requested: {}", sessionId);

        return fileSessionRepository
                .findByIdAndActive(sessionId, true)
                .map(session -> {
                    session.setInfo("session was updated by request: timestamp=" + Instant.now());
                    return true;
                }).orElseGet(() -> {
                    LOGGER.warn("No sessions were found by id = {}", sessionId);
                    return false;
                });
    }

    @Override
    @Transactional(propagation = NESTED, isolation = READ_COMMITTED)
    public boolean hasActiveUserSessions() {

        LOGGER.trace("Requesting active user sessions");

        final List<FileSessionEntity> possiblyActiveSessions = findActiveSessionsWithActualIndex();

        final List<FileSessionEntity> activeSessions = possiblyActiveSessions.stream()
                .filter(session -> !isExpired(session)).collect(toList());
        final List<FileSessionEntity> notActiveSessions = possiblyActiveSessions.stream()
                .filter(this::isExpired).collect(toList());

        if (!notActiveSessions.isEmpty()) {
            LOGGER.warn("There are not active sessions which were not deactivated: {}", notActiveSessions);
        }

        LOGGER.debug("The following user sessions are active: {}", activeSessions);
        return !activeSessions.isEmpty();
    }

    @Override
    @Transactional(propagation = MANDATORY,isolation = READ_COMMITTED)
    public void cleanExpiredSessions() {

        /* Closing sessions with index */
        final List<FileSessionEntity> indexedFileSessionList = findActiveSessionsWithActualIndex();
        closeExpiredSessionList(indexedFileSessionList);
        LOGGER.debug("Expired sessions with index were checked");

        /* Closing sessions with not actual index */
        final List<FileSessionEntity> fileSessionList = findActiveSessions();
        closeExpiredSessionList(fileSessionList);
        LOGGER.debug("Expired sessions without index were checked");
    }


    // Internal methods:-----------------------------------------------------------------
    private List<FileSessionEntity> findActiveSessionsWithActualIndex() {

        final Optional<FileSysIndexEntity> fileSysIndexOpt = fileSysIndexEditor.findAppliedActualIndex();

        if (fileSysIndexOpt.isPresent()) {
            final List<FileSessionEntity> fileSessionList = fileSessionRepository
                    .findAllByActiveAndFileSysIndex(true, fileSysIndexOpt.get());

            LOGGER.debug("Requested active sessions with actual index: {}", fileSessionList);
            return fileSessionList;
        } else {
            throw new IllegalStateException("No actual index was found");
        }
    }

    private List<FileSessionEntity> findActiveSessions() {

        final List<FileSessionEntity> fileSessionList = fileSessionRepository
                .findAllByActive(true);

        LOGGER.debug("Requested active sessions: {}", fileSessionList);
        return fileSessionList;
    }

    private void closeExpiredSessionList(List<FileSessionEntity> fileSessionList) {
        for (FileSessionEntity fileSession : fileSessionList) {
            if (isExpired(fileSession)) {
                fileSession.setActive(false);
                fileSession.setInfo("the session was closed automatically: prev. info='" + fileSession.getInfo() + "'");
                LOGGER.trace("The session {} was closed automatically", fileSession);
            }
        }
    }

    private boolean isExpired(FileSessionEntity session) {
        final boolean expired = isExpired(session.getUpdatedAt());
        LOGGER.trace("The following session is expired: {}", expired);
        return expired;
    }

    private boolean isExpired(Instant expirationTime) {
        Instant now = Instant.now();
        now = now.minusMillis(appConfig.getUserSessionExpirationTimeout());
        return now.isAfter(expirationTime);
    }

}
