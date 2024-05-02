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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.onlyoffice.integration.documentserver.models.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DefaultFormatService implements FormatService {
    private List<Format> formats;
    @Autowired
    public DefaultFormatService(
            @Value("classpath:assets/document-formats/onlyoffice-docs-formats.json") final Resource resourceFile,
            final ObjectMapper objectMapper
    ) {
        try {
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            File targetFile = resourceFile.getFile();
            this.formats = objectMapper.readValue(targetFile, new TypeReference<List<Format>>() { });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Format> getFormats() {
        return this.formats;
    }

    public List<Format> getFormatsByAction(final String action) {
        return this
                .formats
                .stream()
                .filter(format -> format.getActions().contains(action))
                .collect(Collectors.toList());
    }

    public List<String> allExtensions() {
        return this
                .formats
                .stream()
                .map(format -> format.getName())
                .collect(Collectors.toList());
    }

    public List<String> fillableExtensions() {
        return this
                .getFormatsByAction("fill")
                .stream()
                .map(format -> format.getName())
                .collect(Collectors.toList());
    }

    public List<String> viewableExtensions() {
        return this
                .getFormatsByAction("view")
                .stream()
                .map(format -> format.getName())
                .collect(Collectors.toList());
    }

    public List<String> editableExtensions() {
        return Stream
                .of(this.getFormatsByAction("edit"), this.getFormatsByAction("lossy-edit"))
                .flatMap(x -> x.stream())
                .map(format -> format.getName())
                .collect(Collectors.toList());
    }

    public List<String> autoConvertExtensions() {
        return this
                .getFormatsByAction("auto-convert")
                .stream()
                .map(format -> format.getName())
                .collect(Collectors.toList());
    }
}
