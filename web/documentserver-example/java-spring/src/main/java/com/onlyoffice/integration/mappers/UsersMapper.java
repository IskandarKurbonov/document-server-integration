// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.mappers;

import com.onlyoffice.integration.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Primary
public class UsersMapper extends AbstractMapper<User, com.onlyoffice.integration.documentserver.models.filemodel.User> {
    @Autowired
    private ModelMapper mapper;

    public UsersMapper() {
        super(com.onlyoffice.integration.documentserver.models.filemodel.User.class);
    }

    @PostConstruct
    public void configure() {  // configure the users mapper
        mapper.createTypeMap(User.class, com.onlyoffice.integration.documentserver.models.filemodel
                        .User.class)  // create the type map
                .setPostConverter(modelConverter());  // and apply the post converter to it
    }

    @Override
    public void handleSpecificFields(final User source,
                                     final com.onlyoffice.integration.documentserver.models.filemodel
                                             .User destination) {  // handle specific users fields
        destination.setGroup(source.getGroup() != null
                ? source.getGroup().getName() : null);  // set the Group parameter
    }
}
