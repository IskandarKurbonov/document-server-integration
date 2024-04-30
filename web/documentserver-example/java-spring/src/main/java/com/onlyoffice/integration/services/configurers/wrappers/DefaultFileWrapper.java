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

package com.onlyoffice.integration.services.configurers.wrappers;

import com.onlyoffice.integration.documentserver.models.enums.Action;
import com.onlyoffice.integration.entities.User;
import com.onlyoffice.integration.documentserver.models.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class DefaultFileWrapper {
    private String fileName;
    private Type type;
    private User user;
    private String lang;
    private Action action;
    private String actionData;
    private Boolean canEdit;
    private Boolean isEnableDirectUrl;
}
