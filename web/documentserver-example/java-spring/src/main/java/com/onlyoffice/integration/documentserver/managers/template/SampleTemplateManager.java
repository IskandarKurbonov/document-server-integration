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

import com.onlyoffice.integration.documentserver.models.enums.DocumentType;
import com.onlyoffice.integration.documentserver.models.filemodel.Template;
import com.onlyoffice.integration.documentserver.managers.document.DocumentManager;
import com.onlyoffice.integration.documentserver.storage.FileStoragePathBuilder;
import com.onlyoffice.integration.documentserver.util.file.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("sample")
public class SampleTemplateManager implements TemplateManager {
    @Autowired
    private DocumentManager documentManager;

    @Autowired
    private FileStoragePathBuilder storagePathBuilder;

    @Autowired
    private FileUtility fileUtility;

    // create a template document with the specified name
    public List<Template> createTemplates(final String fileName) {
        List<Template> templates = List.of(
                new Template("", "Blank", documentManager
                        .getCreateUrl(fileName, false)),  // create a blank template
                new Template(getTemplateImageUrl(fileName), "With sample content", documentManager
                        .getCreateUrl(fileName,
                                true))  // create a template with sample content using the template image
        );

        return templates;
    }

    // get the template image URL for the specified file
    public String getTemplateImageUrl(final String fileName) {
        DocumentType fileType = fileUtility.getDocumentType(fileName);  // get the file type
        String path = storagePathBuilder.getServerUrl(true);  // get server URL
        if (fileType.equals(DocumentType.word)) {  // get URL to the template image for the word document type
            return path + "/css/img/file_docx.svg";
        } else if (fileType.equals(DocumentType.slide)) {  // get URL to the template image for the slide document type
            return path + "/css/img/file_pptx.svg";
        } else if (fileType.equals(DocumentType.cell)) {  // get URL to the template image for the cell document type
            return path + "/css/img/file_xlsx.svg";
        }
        return path + "/css/img/file_docx.svg";  // get URL to the template image for the default document type (word)
    }
}
