package org.numamo.category.file.info.service.controller.api;

import org.numamo.category.file.info.service.controller.dto.FileDto;
import org.numamo.category.file.info.service.controller.dto.FileProviderRqDto;
import org.numamo.category.file.info.service.controller.dto.FolderDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FileApi {

    @GetMapping("/find/root-folders")
    List<FolderDto> findRootFolders(@RequestBody FileProviderRqDto fileProviderRq);


    @GetMapping("/find/folders")
    List<FolderDto> findFolders (@RequestBody FileProviderRqDto fileProviderRq);


    @GetMapping("/find/root-files")
    List<FileDto> findRootFiles (@RequestBody FileProviderRqDto fileProviderRq);


    @GetMapping("/find/files")
    List<FileDto> findFiles (@RequestBody FileProviderRqDto fileProviderRq);

}
