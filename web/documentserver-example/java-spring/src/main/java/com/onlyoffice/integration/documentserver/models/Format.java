// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.models;

import java.util.List;

import com.onlyoffice.integration.documentserver.models.enums.DocumentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Format {
    private String name;
    private DocumentType type;
    private List<String> actions;
    private List<String> convert;
    private List<String> mime;
}
