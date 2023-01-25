package org.numamo.category.file.info.service.config;

import org.numamo.category.file.info.service.config.api.AppConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;


@Configuration
public class AppConfigImpl implements AppConfig {


    private static final Logger LOGGER = getLogger(AppConfigImpl.class);

    private String categoryFileRoot;
    private String categoryFileName;
    private String userAccessFileName;
    private long userSessionExpirationTimeout;

    @PostConstruct
    void postConstruct () {
        LOGGER.info("The app config was set as: {}",this);
    }

    @Override
    public String getCategoryFileRoot() {
        return categoryFileRoot;
    }

    @Value("${app.category-file-root}")
    void setCategoryFileRoot (String categoryFileRoot) {
        this.categoryFileRoot = requireNonNull(categoryFileRoot);
    }

    @Override
    public String getCategoryFileName() {
        return categoryFileName;
    }

    @Value("${app.category-file-name}")
    public void setCategoryFileName(String categoryFileName) {
        this.categoryFileName = requireNonNull(categoryFileName);
    }

    @Override
    public String getUserAccessFileName() {
        return userAccessFileName;
    }

    @Value("${app.user-access-file-name}")
    public void setUserAccessFileName(String userAccessFileName) {
        this.userAccessFileName = requireNonNull(userAccessFileName);
    }

    @Override
    public long getUserSessionExpirationTimeout() {
        return userSessionExpirationTimeout;
    }

    @Value("${app.user-session-expiration-timeout}")
    public void setUserSessionExpirationTimeout(Long userSessionExpirationTimeout) {
        this.userSessionExpirationTimeout = userSessionExpirationTimeout;
    }

    @Override
    public String toString() {
        return "AppConfigImpl{" +
                "categoryFileRoot='" + categoryFileRoot + '\'' +
                ", categoryFileName='" + categoryFileName + '\'' +
                ", userAccessFileName='" + userAccessFileName + '\'' +
                ", userSessionExpirationTimeout=" + userSessionExpirationTimeout +
                '}';
    }
}
