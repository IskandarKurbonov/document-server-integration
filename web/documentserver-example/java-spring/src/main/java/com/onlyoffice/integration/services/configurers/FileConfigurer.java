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

package com.onlyoffice.integration.services.configurers;

import com.onlyoffice.integration.documentserver.models.filemodel.FileModel;

public interface FileConfigurer<W> extends Configurer<FileModel, W> {
    void configure(FileModel model, W wrapper);
    FileModel getFileModel(W wrapper);
}
