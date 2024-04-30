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

import com.onlyoffice.integration.documentserver.models.filemodel.EditorConfig;

public interface EditorConfigConfigurer<W> extends Configurer<EditorConfig, W> {
    void configure(EditorConfig editorConfig, W wrapper);
}
