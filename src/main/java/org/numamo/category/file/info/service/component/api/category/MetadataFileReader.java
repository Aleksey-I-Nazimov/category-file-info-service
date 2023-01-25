package org.numamo.category.file.info.service.component.api.category;

import org.aspectj.apache.bcel.classfile.Code;
import org.numamo.category.file.info.service.component.api.category.model.CodeDmo;
import org.numamo.category.file.info.service.controller.dto.CategoryDto;
import org.numamo.category.file.info.service.component.api.category.model.UserAccessDmo;

import java.io.File;
import java.util.List;


public interface MetadataFileReader {

    List<CodeDmo> readCategories (File codeFile);

    List<UserAccessDmo> readAccesses (File accessFile);

}
