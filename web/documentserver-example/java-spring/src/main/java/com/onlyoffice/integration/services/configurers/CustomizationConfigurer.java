// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.services.configurers;

import com.onlyoffice.integration.documentserver.models.configurations.Customization;

public interface CustomizationConfigurer<W> extends Configurer<Customization, W> {
    void configure(Customization customization, W wrapper);
}
