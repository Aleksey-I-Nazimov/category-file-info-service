package org.numamo.category.file.info.service.component.category;

import org.numamo.category.file.info.service.component.api.category.CategoryReader;
import org.numamo.category.file.info.service.component.api.category.MetadataFileReader;
import org.numamo.category.file.info.service.component.api.category.file.FileComponent;
import org.numamo.category.file.info.service.component.api.category.model.CategoryDmo;
import org.numamo.category.file.info.service.component.api.category.model.CodeDmo;
import org.numamo.category.file.info.service.component.api.category.model.FileObjectDmo;
import org.numamo.category.file.info.service.component.api.category.model.UserAccessDmo;
import org.numamo.category.file.info.service.config.api.AppConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;


@Component
public final class CategoryReaderImpl implements CategoryReader {

    private static final Logger LOGGER = getLogger(CategoryReaderImpl.class);

    private final AppConfig appConfig;
    private final MetadataFileReader metadataFileReader;
    private final FileComponent fileComponent;

    @Autowired
    public CategoryReaderImpl(
            AppConfig appConfig,
            MetadataFileReader metadataFileReader,
            FileComponent fileComponent
    ) {
        this.appConfig = appConfig;
        this.metadataFileReader = metadataFileReader;
        this.fileComponent = fileComponent;
    }


    @Override
    public List<CategoryDmo> read() {

        final String fileRoot = appConfig.getCategoryFileRoot();

        final List<CodeDmo> codes = searchCategoryFile(new File(fileRoot));

        final List<CategoryDmo> categoryList = codes.stream()
                .map(code -> {
                    final File root = searchCategoryRootFolder(fileRoot, code);
                    final FileObjectDmo fileObject = makeRoots(root, null);
                    return new CategoryDmo(code, fileObject);
                })
                .collect(toList());

        LOGGER.debug("Read all categories: {}", categoryList);
        return unmodifiableList(categoryList);
    }


    private List<CodeDmo> searchCategoryFile (final File root) {

        final List<File> subFiles = fileComponent.getSubFiles(root);

        for (final File subFile : subFiles) {
            if (fileComponent.isCategoryFile(subFile) && subFile.isFile()) {
                final List<CodeDmo> categoryCodes = metadataFileReader.readCategories(subFile);
                LOGGER.debug("Read categories file: {} from: {}", categoryCodes, subFile);
                return categoryCodes;
            }
        }

        return emptyList();
    }

    private File searchCategoryRootFolder(
            final String rootFile,
            final CodeDmo code
    ) {
        return fileComponent.getSubFiles(new File(rootFile))
                .stream()
                .filter(f -> Objects.equals(code.getName(), f.getName()) && f.isDirectory())
                .findAny()
                .orElseThrow(() ->
                        new IllegalArgumentException("The category root folder cannot be selected by " + code));
    }

    private FileObjectDmo makeRoots(
            final File root,
            final FileObjectDmo parentFileObject
    ) {
        if (root.isDirectory()) {
            return makeRoot(root,parentFileObject);

        } else if (root.isFile()) {
            final FileObjectDmo fileObject = new FileObjectDmo(root.getName(),
                    parentFileObject, root.getTotalSpace());
            LOGGER.trace("Read file: {}",fileObject);
            return fileObject;

        } else {
            LOGGER.warn("Skipped file: {}",root);
            return null;
        }
    }

    private FileObjectDmo makeRoot(
            final File root,
            final FileObjectDmo parentFileObject
    ) {
        final FileObjectDmo rootFileObject = new FileObjectDmo(root.getName(), parentFileObject);
        rootFileObject.getUserAccessList().addAll(searchAccessFile(root));
        rootFileObject.getChildList().addAll(
                fileComponent
                        .getSubFiles(root)
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(subFile -> !fileComponent.isUserAccessFile(subFile))
                        .map(subFile -> makeRoots(subFile, rootFileObject))
                        .filter(Objects::nonNull)
                        .collect(toList())
        );
        LOGGER.trace("Read root: {}",rootFileObject);
        return rootFileObject;
    }

    private List<UserAccessDmo> searchAccessFile(
            final File root
    ) {
        final List<File> subFiles = fileComponent.getSubFiles(root);

        for (final File subFile : subFiles) {
            if (fileComponent.isUserAccessFile(subFile) && subFile.isFile()) {
                final List<UserAccessDmo> userAccessList = metadataFileReader.readAccesses(subFile);
                LOGGER.debug("Read access file: {} from: {}", userAccessList, subFile);
                return userAccessList;
            }
        }

        return emptyList();
    }

}
