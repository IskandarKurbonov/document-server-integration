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

package entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public enum FileType {
    @JsonProperty("word")
    @SerializedName("word")
    WORD,
    @JsonProperty("cell")
    @SerializedName("cell")
    CELL,
    @JsonProperty("slide")
    @SerializedName("slide")
    SLIDE
}
