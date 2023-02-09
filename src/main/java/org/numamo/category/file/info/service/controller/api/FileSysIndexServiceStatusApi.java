package org.numamo.category.file.info.service.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.numamo.category.file.info.service.config.SecurityConfig.USER_API_PREFIX;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;

public interface FileSysIndexServiceStatusApi {

    @RequestMapping(method = PATCH, path = USER_API_PREFIX + "/change/sys-index-status/enable")
    ResponseEntity<Boolean> enableSysIndexStatus();

    @RequestMapping(method = PATCH, path = USER_API_PREFIX + "/change/sys-index-status/disable")
    ResponseEntity<Boolean> disableSysIndexStatus();

    @RequestMapping(method = GET, path = USER_API_PREFIX + "/get/sys-index-status")
    ResponseEntity<Boolean> isSysIndexEnabled();

}
