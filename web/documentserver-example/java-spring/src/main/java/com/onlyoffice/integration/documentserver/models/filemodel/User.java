// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.models.filemodel;

import com.onlyoffice.integration.documentserver.models.AbstractModel;
import com.onlyoffice.integration.documentserver.storage.FileStoragePathBuilder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Getter
@Setter
public class User extends AbstractModel {
    private FileStoragePathBuilder storagePathBuilder;
    private String id;
    private String name;
    private String group;
    private String image;

    // the user configuration parameters
    public void configure(final int idParam, final String nameParam, final String groupParam) {
        this.id = "uid-" + idParam;  // the user id
        this.name = nameParam;  // the user name
        this.group = groupParam;  // the group the user belongs to
        this.image = storagePathBuilder.getServerUrl(true) + "/css/img/uid-" + this.id + ".png";
    }
}
