// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.services.configurers.wrappers;

import com.onlyoffice.integration.documentserver.models.enums.Action;
import com.onlyoffice.integration.entities.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultCustomizationWrapper {
    private Action action;
    private User user;
}
