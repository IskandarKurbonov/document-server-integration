// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.models.filemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onlyoffice.integration.documentserver.serializers.SerializerFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentGroup {
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = SerializerFilter.class)
    private List<String> view;  // define a list of groups whose comments the user can view
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = SerializerFilter.class)
    private List<String> edit;  // define a list of groups whose comments the user can edit
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = SerializerFilter.class)
    private List<String> remove;  // define a list of groups whose comments the user can remove
}
