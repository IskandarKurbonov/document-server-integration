// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Mentions {
    private String name;
    private String email;
}
