//  SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
//  SPDX-License-Identifier: Ascensio-System
//
//     Our License onlyoffice.com
//     Empty line
//     Empty line
//     Empty line
//     Empty line
//     Empty line
//     Empty line
//     Empty line
//     

package com.onlyoffice.integration.services;

import com.onlyoffice.integration.entities.Group;
import com.onlyoffice.integration.entities.Permission;
import com.onlyoffice.integration.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServices {

    @Autowired
    private PermissionRepository permissionRepository;

    // create permissions with the specified parameters
    public Permission createPermission(final List<Group> reviewGroups,
                                       final List<Group> commentViewGroups,
                                       final List<Group> commentEditGroups,
                                       final List<Group> commentRemoveGroups,
                                       final List<Group> userInfoGroups,
                                       final Boolean chat,
                                       final Boolean protect) {

        Permission permission = new Permission();
        permission.setReviewGroups(reviewGroups);  // define the groups whose changes the user can accept/reject
        permission.setCommentsViewGroups(commentViewGroups);  // defines the groups whose comments the user can view
        permission.setCommentsEditGroups(commentEditGroups);  // defines the groups whose comments the user can edit
        permission.setCommentsRemoveGroups(commentRemoveGroups);  /* defines the groups
         whose comments the user can remove */
        permission.setUserInfoGroups(userInfoGroups);
        permission.setChat(chat);
        permission.setProtect(protect);

        permissionRepository.save(permission);  // save new permissions

        return permission;
    }

    // update permissions
    public Permission updatePermission(final Permission newPermission) {
        permissionRepository.save(newPermission);  // save new permissions

        return newPermission;
    }
}
