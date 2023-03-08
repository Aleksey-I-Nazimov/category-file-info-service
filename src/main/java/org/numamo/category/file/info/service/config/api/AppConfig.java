package org.numamo.category.file.info.service.config.api;


public interface AppConfig {

    String getCategoryFileRoot();

    String getCategoryFileName();

    String getUserAccessFileName();

    long getUserSessionExpirationTimeout();

    String getDefaultUserLogin();

    String getDefaultUserPassword();

}
