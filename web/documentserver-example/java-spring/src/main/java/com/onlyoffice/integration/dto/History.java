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

package com.onlyoffice.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onlyoffice.integration.documentserver.models.filemodel.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @JsonProperty("serverVersion")
    private String serverVersion;
    private String key;
    private Integer version;
    private String created;
    private User user;
    private List<ChangesHistory> changes;
}
