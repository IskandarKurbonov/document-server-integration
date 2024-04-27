// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.callbacks.implementations;

import com.onlyoffice.integration.documentserver.callbacks.Callback;
import com.onlyoffice.integration.documentserver.callbacks.Status;
import com.onlyoffice.integration.documentserver.managers.callback.CallbackManager;
import com.onlyoffice.integration.dto.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForcesaveCallback implements Callback {
    @Autowired
    private CallbackManager callbackManager;
    @Override
    public int handle(final Track body,
                      final String fileName) {  // handle the callback when the force saving request is performed
        int result = 0;
        try {
            callbackManager.processForceSave(body, fileName);  // file force saving process
        } catch (Exception ex) {
            ex.printStackTrace();
            result = 1;
        }
        return result;
    }

    @Override
    public int getStatus() {  // get document status
        // return status 6 - document is being edited, but the current document state is saved
        return Status.MUST_FORCE_SAVE.getCode();
    }
}
