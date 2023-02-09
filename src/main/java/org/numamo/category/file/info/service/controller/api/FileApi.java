package org.numamo.category.file.info.service.controller.api;

import org.numamo.category.file.info.service.controller.dto.FileDto;
import org.numamo.category.file.info.service.controller.dto.FolderDto;
import org.numamo.category.file.info.service.controller.dto.ProviderRqDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FileApi {

    @PostMapping("/find/root-folders")
    List<FolderDto> findRootFolders(@RequestBody ProviderRqDto providerRequest);


    @PostMapping("/find/folders")
    List<FolderDto> findFolders(@RequestBody ProviderRqDto providerRequest);


    @PostMapping("/find/files")
    List<FileDto> findFiles(@RequestBody ProviderRqDto providerRequest);

}
