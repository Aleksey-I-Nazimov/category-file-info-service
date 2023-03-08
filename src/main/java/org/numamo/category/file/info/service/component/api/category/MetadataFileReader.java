package org.numamo.category.file.info.service.component.api.category;

import org.numamo.category.file.info.service.component.api.category.model.CodeDmo;
import org.numamo.category.file.info.service.component.api.category.model.UserAccessDmo;

import java.io.File;
import java.util.List;


public interface MetadataFileReader {

    List<CodeDmo> readCategories(File codeFile);

    List<UserAccessDmo> readAccesses(File accessFile);

}
