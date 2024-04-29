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
import com.onlyoffice.integration.dto.Action;
import com.onlyoffice.integration.dto.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditCallback implements Callback {
    @Autowired
    private CallbackManager callbackManager;
    @Override
    public int handle(final Track body,
                      final String fileName) {  // handle the callback when the document is being edited
        int result = 0;
        Action action =  body.getActions().get(0);  // get the user ID who is editing the document
        if (action.getType().equals(com.onlyoffice.integration.documentserver.models.enums
                .Action.edit)) {  // if this value is not equal to the user ID
            String user =  action.getUserid();  // get user ID
            if (!body.getUsers().contains(user)) {  // if this user is not specified in the body
                String key = body.getKey();  // get document key
                try {
                    // create a command request to forcibly save the document being edited without closing it
                    callbackManager.commandRequest("forcesave", key, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    result = 1;
                }
            }
        }
        return result;
    }

    @Override
    public int getStatus() {  // get document status
        return Status.EDITING.getCode();  // return status 1 - document is being edited
    }
}
