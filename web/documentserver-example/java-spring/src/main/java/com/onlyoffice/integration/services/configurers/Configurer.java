// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.services.configurers;

public interface Configurer<O, W> {
    void configure(O instance, W wrapper);
}
