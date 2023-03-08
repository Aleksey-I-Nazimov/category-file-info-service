package org.numamo.category.file.info.service.component.api.category.file;

import java.io.File;
import java.util.List;

public interface FileComponent {

    boolean isCategoryFile(File anyFile);

    boolean isUserAccessFile(File anyFile);

    List<File> getSubFiles(File fileRoot);
}
