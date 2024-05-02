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

package com.onlyoffice.integration.documentserver.managers.template;

import com.onlyoffice.integration.documentserver.models.filemodel.Template;
import java.util.List;

// specify the template manager functions
public interface TemplateManager {
    List<Template> createTemplates(String fileName);  // create a template document with the specified name
    String getTemplateImageUrl(String fileName);  // get the template image URL for the specified file
}
