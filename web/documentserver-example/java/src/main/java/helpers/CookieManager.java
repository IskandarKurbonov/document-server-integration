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

package helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieManager {
    private HashMap<String, String> cookiesMap;

    public CookieManager(final HttpServletRequest request) throws UnsupportedEncodingException {
        cookiesMap = new HashMap<String, String>();

        Cookie[] cookies = request.getCookies();  // get all the cookies from the request
        if (cookies != null) {
            for (Cookie cookie : cookies) {  // run through all the cookies

                // add cookie to the cookies map if its name isn't in the map yet
                cookiesMap.putIfAbsent(cookie.getName(), URLDecoder.decode(cookie.getValue(), "UTF-8"));
            }
        }
    }

    // get cookie by its name
    public String getCookie(final String name) {
        return cookiesMap.get(name);
    }
}
