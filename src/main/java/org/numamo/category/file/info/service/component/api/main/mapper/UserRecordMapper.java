package org.numamo.category.file.info.service.component.api.main.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.numamo.category.file.info.service.component.api.category.model.UserAccessDmo;
import org.numamo.category.file.info.service.component.api.main.user.model.GrantedAuthorityDmo;
import org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo;
import org.numamo.category.file.info.service.repository.entity.user.UserInfoEntity;
import org.numamo.category.file.info.service.repository.entity.user.UserRecordEntity;

import java.util.ArrayList;
import java.util.List;

import static org.numamo.category.file.info.service.component.api.main.user.model.UserDetailsDmo.USER_AUTHORITY;

@Mapper(componentModel = "spring")
public interface UserRecordMapper {


    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "login", source = "login")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "userInfo", ignore = true)
    UserRecordEntity makeNewUserRecord(
            long id,
            String login,
            String code
    );


    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "login", source = "userAccess.login")
    @Mapping(target = "code", source = "userAccess.code")
    @Mapping(target = "userInfo", ignore = true)
    UserRecordEntity makeNewUserRecord(
            long id,
            UserAccessDmo userAccess
    );


    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", source = "userInfo.password")
    @Mapping(target = "login", source = "login")
    @Mapping(target = "userName", source = "userInfo.firstName")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "accountExpired", constant = "false")
    @Mapping(target = "accountLocked", constant = "false")
    @Mapping(target = "credentialsExpired", source = "userInfo.tokenExpired")
    @Mapping(target = "enabled", source = "userInfo.enabled")
    UserDetailsDmo makeUserDetails(UserRecordEntity userRecordEntity);


    @AfterMapping()
    default void updateUserDetails(
            UserRecordEntity userRecordEntity,
            @MappingTarget UserDetailsDmo userDetails
    ) {
        final GrantedAuthorityDmo authority = new GrantedAuthorityDmo();
        authority.setAuthority(USER_AUTHORITY);

        final List<GrantedAuthorityDmo> authorityList = new ArrayList<>();
        authorityList.add(authority);
        userDetails.setAuthorities(authorityList);
    }


    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "userDetails.code")
    @Mapping(target = "login", source = "userDetails.login")
    @Mapping(target = "userInfo", source = "userInfo")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserRecordEntity makeUserRecord(long id, UserDetailsDmo userDetails, UserInfoEntity userInfo);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "userDetailsDmo.userName")
    @Mapping(target = "lastName", constant = "auto-created-name")
    @Mapping(target = "email", constant = "auto@auto.ru")
    @Mapping(target = "password", source = "userDetailsDmo.password")
    @Mapping(target = "enabled", source = "userDetailsDmo.enabled")
    @Mapping(target = "tokenExpired", ignore = true)
    @Mapping(target = "userRecord", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserInfoEntity makeUserInfo(long id, UserDetailsDmo userDetailsDmo);

    @AfterMapping
    default void updateUserInfo(UserDetailsDmo userDetailsDmo, @MappingTarget UserInfoEntity userInfoEntity) {
        userInfoEntity.setTokenExpired(!userDetailsDmo.isCredentialsNonExpired());
    }

}
