// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.services.configurers;

import com.onlyoffice.integration.documentserver.models.filemodel.EditorConfig;

public interface EditorConfigConfigurer<W> extends Configurer<EditorConfig, W> {
    void configure(EditorConfig editorConfig, W wrapper);
}
