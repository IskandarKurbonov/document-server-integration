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

package com.onlyoffice.integration.documentserver.callbacks;

import com.onlyoffice.integration.dto.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CallbackHandler {

    private Logger logger = LoggerFactory.getLogger(CallbackHandler.class);

    private Map<Integer, Callback> callbackHandlers = new HashMap<>();

    public void register(final int code, final Callback callback) {  // register a callback handler
        callbackHandlers.put(code, callback);
    }

    public int handle(final Track body, final String fileName) {  // handle a callback
        Callback callback = callbackHandlers.get(body.getStatus());
        if (callback == null) {
            logger.warn("Callback status " + body.getStatus() + " is not supported yet");
           return 0;
        }

        int result = callback.handle(body, fileName);
        return result;
    }
}
