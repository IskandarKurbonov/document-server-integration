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

package com.onlyoffice.integration.documentserver.util.service;

import com.onlyoffice.integration.documentserver.models.Format;

import java.util.List;

public interface FormatService {
    List<Format> getFormats();
    List<Format> getFormatsByAction(String action);
    List<String> allExtensions();
    List<String> fillableExtensions();
    List<String> viewableExtensions();
    List<String> editableExtensions();
    List<String> autoConvertExtensions();
}
