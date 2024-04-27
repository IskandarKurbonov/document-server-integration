// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.services.configurers.implementations;

import com.onlyoffice.integration.documentserver.models.enums.Action;
import com.onlyoffice.integration.documentserver.models.configurations.Customization;
import com.onlyoffice.integration.entities.User;
import com.onlyoffice.integration.services.configurers.CustomizationConfigurer;
import com.onlyoffice.integration.services.configurers.wrappers.DefaultCustomizationWrapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class DefaultCustomizationConfigurer implements CustomizationConfigurer<DefaultCustomizationWrapper> {
    @Override
    // define the customization configurer
    public void configure(final Customization customization, final DefaultCustomizationWrapper wrapper) {
        Action action = wrapper.getAction();  // get the action parameter from the customization wrapper
        User user = wrapper.getUser();
    }
}
