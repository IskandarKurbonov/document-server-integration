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

import com.onlyoffice.integration.documentserver.models.filemodel.Permission;
import com.onlyoffice.integration.documentserver.models.filemodel.ReferenceData;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultDocumentWrapper {
    private Permission permission;
    private String fileName;
    private Boolean favorite;
    private Boolean isEnableDirectUrl;
    private ReferenceData referenceData;
}
