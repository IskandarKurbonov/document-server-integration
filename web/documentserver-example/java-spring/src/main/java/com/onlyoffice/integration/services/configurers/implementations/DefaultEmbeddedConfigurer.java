// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.services.configurers.implementations;

import com.onlyoffice.integration.documentserver.models.configurations.Embedded;
import com.onlyoffice.integration.documentserver.models.enums.ToolbarDocked;
import com.onlyoffice.integration.documentserver.models.enums.Type;
import com.onlyoffice.integration.services.configurers.EmbeddedConfigurer;
import com.onlyoffice.integration.services.configurers.wrappers.DefaultEmbeddedWrapper;
import com.onlyoffice.integration.documentserver.managers.document.DocumentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class DefaultEmbeddedConfigurer implements EmbeddedConfigurer<DefaultEmbeddedWrapper> {

    @Autowired
    private DocumentManager documentManager;

    public void configure(final Embedded embedded,
                          final DefaultEmbeddedWrapper wrapper) {  // define the embedded configurer
        if (wrapper.getType().equals(Type.embedded)) {  // check if the type from the embedded wrapper is embedded
            String url = documentManager.getDownloadUrl(wrapper
                    .getFileName(), false);  // get file URL of the specified file

            /* set the embedURL parameter to the embedded config (the absolute URL to the document serving
             as a source file for the document embedded into the web page) */
            embedded.setEmbedUrl(url);

            /* set the saveURL parameter to the embedded config (the absolute URL that will allow
             the document to be saved onto the user personal computer) */
            embedded.setSaveUrl(url);

            /* set the shareURL parameter to the embedded config (the absolute URL
             that will allow other users to share this document) */
            embedded.setShareUrl(url);

            /* set the top toolbarDocked parameter to the embedded config (the place for the
             embedded viewer toolbar, can be either top or bottom) */
            embedded.setToolbarDocked(ToolbarDocked.top);
        }
    }
}
