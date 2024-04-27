// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.services;

import com.onlyoffice.integration.entities.Group;
import com.onlyoffice.integration.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServices {

    @Autowired
    private GroupRepository groupRepository;

    // create a new group with the specified name
    public Group createGroup(final String name) {
        if (name == null) {
            return null;  // check if a name is specified
        }
        Optional<Group> group = groupRepository
                .findGroupByName(name);  // check if group with such a name already exists
        if (group.isPresent()) {
            return group.get();  // if it exists, return it
        }
        Group newGroup = new Group();
        newGroup.setName(name);  // otherwise, create a new group with the specified name

        groupRepository.save(newGroup);  // save a new group

        return newGroup;
    }

    // create a list of groups from the reviewGroups permission parameter
    public List<Group> createGroups(final List<String> reviewGroups) {
        if (reviewGroups == null) {
            return null;  // check if the reviewGroups permission exists
        }
        // convert this parameter to a list of groups whose changes the user can accept/reject
        return reviewGroups.stream()
                .map(group -> createGroup(group))
                .collect(Collectors.toList());
    }
}
