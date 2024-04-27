// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.services;

import com.onlyoffice.integration.entities.Group;
import com.onlyoffice.integration.entities.Permission;
import com.onlyoffice.integration.entities.User;
import com.onlyoffice.integration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupServices groupServices;

    @Autowired
    private PermissionServices permissionService;

    // get a list of all users
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // get a user by their ID
    public Optional<User> findUserById(final Integer id) {
        return userRepository.findById(id);
    }

    // create a user with the specified parameters
    public User createUser(final String name, final String email,
                           final List<String> description, final String group,
                           final List<String> reviewGroups,
                           final List<String> viewGroups,
                           final List<String> editGroups,
                           final List<String> removeGroups,
                           final List<String> userInfoGroups, final Boolean favoriteDoc,
                           final Boolean chat,
                           final Boolean protect,
                           final Boolean avatar) {
        User newUser = new User();
        newUser.setName(name);  // set the user name
        newUser.setEmail(email);  // set the user email
        newUser.setGroup(groupServices.createGroup(group));  // set the user group
        newUser.setDescriptions(description);  // set the user description
        newUser.setFavorite(favoriteDoc);  // specify if the user has the favorite documents or not
        newUser.setAvatar(avatar);

        List<Group> groupsReview = groupServices
                .createGroups(reviewGroups);  // define the groups whose changes the user can accept/reject
        List<Group> commentGroupsView = groupServices
                .createGroups(viewGroups);  // defines the groups whose comments the user can view
        List<Group> commentGroupsEdit = groupServices
                .createGroups(editGroups);  // defines the groups whose comments the user can edit
        List<Group> commentGroupsRemove = groupServices
                .createGroups(removeGroups);  // defines the groups whose comments the user can remove
        List<Group> usInfoGroups = groupServices.createGroups(userInfoGroups);

        Permission permission = permissionService
                .createPermission(groupsReview,
                        commentGroupsView,
                        commentGroupsEdit,
                        commentGroupsRemove,
                        usInfoGroups,
                        chat,
                        protect);  // specify permissions for the current user
        newUser.setPermissions(permission);

        userRepository.save(newUser); // save a new user

        return newUser;
    }
}
