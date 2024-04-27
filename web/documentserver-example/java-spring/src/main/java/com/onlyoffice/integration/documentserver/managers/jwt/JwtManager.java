// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.managers.jwt;

import org.json.simple.JSONObject;
import org.primeframework.jwt.domain.JWT;

import java.util.Map;

// specify the jwt manager functions
public interface JwtManager {
    boolean tokenEnabled();  // check if the token is enabled
    boolean tokenUseForRequest();  // check if the token is enabled
    String createToken(Map<String, Object> payloadClaims);  // create document token
    JWT readToken(String token);  // read document token
    JSONObject parseBody(String payload, String header);  // parse the body
}
