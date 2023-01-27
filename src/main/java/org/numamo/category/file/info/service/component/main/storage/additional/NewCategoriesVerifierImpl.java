package org.numamo.category.file.info.service.component.main.storage.additional;

import org.numamo.category.file.info.service.component.api.category.model.CategoryDmo;
import org.numamo.category.file.info.service.component.api.main.storage.additional.NewCategoriesVerifier;
import org.numamo.category.file.info.service.repository.api.dictionary.FileAccessDescriptorRepository;
import org.numamo.category.file.info.service.repository.api.dictionary.FileExtensionRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.numamo.category.file.info.service.component.api.category.model.UserAccessDmo.trimCodes;
import static org.slf4j.LoggerFactory.getLogger;


@Component
public class NewCategoriesVerifierImpl implements NewCategoriesVerifier {

    private static final Logger LOGGER = getLogger(NewCategoriesVerifierImpl.class);

    private final FileAccessDescriptorRepository fileAccessDescriptorRepository;
    private final FileExtensionRepository fileExtensionRepository;


    @Autowired
    public NewCategoriesVerifierImpl(
            FileAccessDescriptorRepository fileAccessDescriptorRepository,
            FileExtensionRepository fileExtensionRepository
    ) {
        this.fileAccessDescriptorRepository = fileAccessDescriptorRepository;
        this.fileExtensionRepository = fileExtensionRepository;
    }


    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public void check(List<CategoryDmo> categories) {

        LOGGER.debug("Requesting for new categories verification");

        controlUserAccesses(categories);
        controlFileExtensions(categories);

        LOGGER.trace("Categories verification was completed!");
    }

    private void controlUserAccesses(
            final List<CategoryDmo> categories
    ) {
        final List<String> newCodes = new ArrayList<>(trimCodes(categories.stream()
                .flatMap(cat -> cat.getFileObject().getAllUsers().stream()).collect(toList())));

        final List<String> orgCodes = new ArrayList<>();
        fileAccessDescriptorRepository.findAll().forEach(r -> orgCodes.add(r.getCode()));
        newCodes.removeAll(orgCodes);
        if (!newCodes.isEmpty()) {
            throw new IllegalStateException("Required codes are not in the dictionary: " + newCodes);
        }
        LOGGER.trace("Checking user access dictionaries was completed");
    }

    private void controlFileExtensions(
            final List<CategoryDmo> categories
    ) {
        final List<String> newExtensions = new ArrayList<>(new HashSet<>(categories.stream()
                .flatMap(cat -> cat.getFileObject().getAllExtensions().stream()).collect(toList())));

        final List<String> extensions = new ArrayList<>();
        fileExtensionRepository.findAll().forEach(r -> extensions.add(r.getCode()));
        newExtensions.removeAll(extensions);
        if (!newExtensions.isEmpty()) {
            throw new IllegalStateException("Required extensions are not in the dictionary: " + newExtensions);
        }
        LOGGER.trace("Checking file extensions was completed");
    }
}
