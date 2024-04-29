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

package com.onlyoffice.integration.documentserver.callbacks.implementations;

import com.onlyoffice.integration.documentserver.callbacks.Callback;
import com.onlyoffice.integration.documentserver.callbacks.Status;
import com.onlyoffice.integration.documentserver.managers.callback.CallbackManager;
import com.onlyoffice.integration.dto.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveCallback implements Callback {
    @Autowired
    private CallbackManager callbackManager;
    @Override
    public int handle(final Track body,
                      final String fileName) {  // handle the callback when the saving request is performed
        int result = 0;
        try {
            callbackManager.processSave(body, fileName);  // file saving process
        } catch (Exception ex) {
            ex.printStackTrace();
            result = 1;
        }

        return result;
    }

    @Override
    public int getStatus() {  // get document status
        return Status.SAVE.getCode();  // return status 2 - document is ready for saving
    }
}
