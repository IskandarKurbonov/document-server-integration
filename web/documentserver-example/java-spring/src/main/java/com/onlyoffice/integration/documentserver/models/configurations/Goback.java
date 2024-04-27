// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.models.configurations;

import com.onlyoffice.integration.documentserver.storage.FileStoragePathBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("prototype")
public class Goback {  // the settings for the Open file location menu button and upper right corner button

    @Autowired
    private FileStoragePathBuilder storagePathBuilder;

    @Value("${url.index}")
    private String indexMapping;

    @Getter
    private String url;  /* the absolute URL to the website address which will be opened
    when clicking the Open file location menu button */

    @PostConstruct
    private void init() {
        this.url = storagePathBuilder.getServerUrl(false) + indexMapping;
    }
}
