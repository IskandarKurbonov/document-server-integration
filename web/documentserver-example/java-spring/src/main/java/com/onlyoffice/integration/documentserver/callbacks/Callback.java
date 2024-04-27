// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.callbacks;

import com.onlyoffice.integration.dto.Track;
import org.springframework.beans.factory.annotation.Autowired;

// specify the callback handler functions
public interface Callback {
    int handle(Track body, String fileName);  // handle the callback
    int getStatus();  // get document status
    @Autowired
    default void selfRegistration(CallbackHandler callbackHandler) {  // register a callback handler
        callbackHandler.register(getStatus(), this);
    }
}
