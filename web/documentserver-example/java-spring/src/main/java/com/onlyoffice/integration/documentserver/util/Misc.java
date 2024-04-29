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

package com.onlyoffice.integration.documentserver.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Misc {

    // convert user descriptions to the specified format
    public String convertUserDescriptions(final String username, final List<String> description) {
        String result = "<div class=\"user-descr\"><b>" + username + "</b><br/><ul>" + description.
                stream().map(text -> "<li>" + text + "</li>")
                .collect(Collectors.joining()) + "</ul></div>";
        return result;
    }
}
