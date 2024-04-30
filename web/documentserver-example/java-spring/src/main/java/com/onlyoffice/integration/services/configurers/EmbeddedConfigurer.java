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

import com.onlyoffice.integration.documentserver.models.configurations.Embedded;

public interface EmbeddedConfigurer<W> extends Configurer<Embedded, W> {
    void configure(Embedded embedded, W wrapper);
}
