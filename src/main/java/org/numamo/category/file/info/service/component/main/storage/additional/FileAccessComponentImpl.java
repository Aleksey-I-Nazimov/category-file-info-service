package org.numamo.category.file.info.service.component.main.storage.additional;

import org.numamo.category.file.info.service.component.api.category.model.FileObjectDmo;
import org.numamo.category.file.info.service.component.api.main.mapper.FileRepoMapper;
import org.numamo.category.file.info.service.component.api.main.storage.additional.FileAccessComponent;
import org.numamo.category.file.info.service.component.api.main.storage.additional.IdGenerator;
import org.numamo.category.file.info.service.repository.api.dictionary.FileAccessDescriptorRepository;
import org.numamo.category.file.info.service.repository.api.user.UserRecordRepository;
import org.numamo.category.file.info.service.repository.entity.FileAccessEntity;
import org.numamo.category.file.info.service.repository.entity.FileEntity;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;


@Component
public class FileAccessComponentImpl implements FileAccessComponent {

    private static final Logger LOGGER = getLogger(FileAccessComponentImpl.class);

    private final IdGenerator idGenerator;
    private final FileRepoMapper fileRepoMapper;
    private final FileAccessDescriptorRepository fileAccessDescriptorRepository;
    private final UserRecordRepository userRecordRepository;

    public FileAccessComponentImpl(
            IdGenerator idGenerator,
            FileRepoMapper fileRepoMapper,
            FileAccessDescriptorRepository fileAccessDescriptorRepository,
            UserRecordRepository userRecordRepository
    ) {
        this.idGenerator = idGenerator;
        this.fileRepoMapper = fileRepoMapper;
        this.fileAccessDescriptorRepository = fileAccessDescriptorRepository;
        this.userRecordRepository = userRecordRepository;
    }


    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<FileAccessEntity> makeAccessEntity(
            final FileObjectDmo fileObject,
            final FileEntity fileEntity
    ) {
        final List<FileAccessEntity> list = fileObject
                .getUserAccessList()
                .stream()
                .map(
                        userAccess -> fileRepoMapper.mapFileAccessEntity(
                                idGenerator.nextId(),
                                fileEntity,
                                fileAccessDescriptorRepository.findByCode(userAccess.getCode()).orElseThrow(
                                        () -> new IllegalArgumentException("Access descriptor cannot be found by code=" + userAccess.getCode())),
                                userRecordRepository.findByLogin(userAccess.getLogin()).orElseThrow(
                                        () -> new IllegalArgumentException("User record was not found by code=" + userAccess.getLogin()))
                        )
                )
                .collect(toList());

        LOGGER.trace("The following file access entity was created: {}", list);
        return list;
    }
}
