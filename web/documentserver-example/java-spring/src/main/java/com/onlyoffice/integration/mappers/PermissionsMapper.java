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

package com.onlyoffice.integration.mappers;

import com.onlyoffice.integration.entities.Permission;
import com.onlyoffice.integration.documentserver.models.filemodel.CommentGroup;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
@Primary
public class PermissionsMapper extends AbstractMapper<Permission,
        com.onlyoffice.integration.documentserver.models.filemodel.Permission> {
    @Autowired
    private ModelMapper mapper;

    public PermissionsMapper() {
        super(com.onlyoffice.integration.documentserver.models.filemodel.Permission.class);
    }

    @PostConstruct
    public void configure() {  // configure the permission mapper
        mapper.createTypeMap(Permission.class, com.onlyoffice.integration.documentserver.models.filemodel
                        .Permission.class)  // create the type map
                .setPostConverter(modelConverter());  // and apply the post converter to it
    }

    @Override
    void handleSpecificFields(final Permission source,
                              final com.onlyoffice.integration.documentserver.models.filemodel
                                      .Permission destination) {  // handle specific permission fields
        destination.setReviewGroups(source.getReviewGroups().stream()
                .map(g -> g.getName())
                .collect(Collectors.toList()));  // set the reviewGroups parameter

        // set the commentGroups parameter
        destination.setCommentGroups(
                new CommentGroup(
                        source.getCommentsViewGroups().stream().map(g -> g.getName()).collect(Collectors.toList()),
                        source.getCommentsEditGroups().stream().map(g -> g.getName()).collect(Collectors.toList()),
                        source.getCommentsRemoveGroups().stream().map(g -> g.getName()).collect(Collectors.toList())
                )
        );
        destination.setUserInfoGroups(source.getUserInfoGroups().stream()
                .map(g -> g.getName())
                .collect(Collectors.toList()));
    }
}
