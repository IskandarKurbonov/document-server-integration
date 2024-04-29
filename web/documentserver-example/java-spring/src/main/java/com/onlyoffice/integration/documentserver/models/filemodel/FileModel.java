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

package com.onlyoffice.integration.documentserver.models.filemodel;

import com.onlyoffice.integration.documentserver.models.enums.DocumentType;
import com.onlyoffice.integration.documentserver.models.enums.Type;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Getter
@Setter
/* the file base parameters which include the platform type used,
 document display size (width and height) and type of the document opened */
public class FileModel {
    @Autowired
    private Document document;  // the parameters pertaining to the document (title, url, file type, etc.)
    private DocumentType documentType;  // the document type to be opened
    @Autowired
    private EditorConfig editorConfig;  /*  the parameters pertaining to the
     editor interface: opening mode (viewer or editor), interface language, additional buttons, etc. */
    private String token;  // the encrypted signature added to the Document Server config
    private Type type;  // the platform type used to access the document
}
