// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.managers.callback;

import com.onlyoffice.integration.dto.Track;
import java.util.HashMap;

public interface CallbackManager {  // specify the callback manager functions
    void processSave(Track body, String fileName);  // file saving process
    void commandRequest(String method, String key, HashMap meta);  // create a command request
    void processForceSave(Track body, String fileName);  // file force saving process
}
