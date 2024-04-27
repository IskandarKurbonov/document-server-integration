// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.services.configurers;

import com.onlyoffice.integration.documentserver.models.configurations.Embedded;

public interface EmbeddedConfigurer<W> extends Configurer<Embedded, W> {
    void configure(Embedded embedded, W wrapper);
}
