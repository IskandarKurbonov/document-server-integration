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

package com.onlyoffice.integration.documentserver.util.file;

import com.onlyoffice.integration.documentserver.models.enums.DocumentType;

import java.nio.file.Path;
import java.util.List;

// specify the file utility functions
public interface FileUtility {
    DocumentType getDocumentType(String fileName);  // get the document type
    String getFileName(String url);  // get file name from its URL
    String getFileNameWithoutExtension(String url);  // get file name without extension
    String getFileExtension(String url);  // get file extension from URL
    String getInternalExtension(DocumentType type);  // get an editor internal extension
    List<String> getFileExts();  // get all the supported file extensions
    List<String> getFillExts();  // get file extensions that can be filled
    List<String> getViewedExts();  // get file extensions that can be viewed
    List<String> getEditedExts();  // get file extensions that can be edited
    List<String> getConvertExts();  // get file extensions that can be converted
    Path generateFilepath(String directory, String fullFileName);  /* generate the file path
     from file directory and name */
    long getMaxFileSize();  // get maximum file size
}
