// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.services.configurers;

import com.onlyoffice.integration.documentserver.models.filemodel.Document;

public interface DocumentConfigurer<W> extends Configurer<Document, W> {
    void configure(Document document, W wrapper);
}
