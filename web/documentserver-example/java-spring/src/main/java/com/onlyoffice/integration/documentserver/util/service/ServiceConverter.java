// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.util.service;

import com.onlyoffice.integration.dto.ConvertedData;
import org.json.simple.JSONObject;
import java.io.InputStream;


// specify the converter service functions
public interface ServiceConverter {
    ConvertedData getConvertedData(String documentUri, String fromExtension,  // get the URL to the converted file
                                   String toExtension, String documentRevisionId,
                                   String filePass, Boolean isAsync, String lang);
    String generateRevisionId(String expectedKey);  // generate document key
    String convertStreamToString(InputStream stream);  // convert stream to string
    JSONObject convertStringToJSON(String jsonString);  // convert string to json
}
