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

package format;

import java.util.List;

import entities.FileType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class Format {

    private String name;
    private FileType type;
    private List<String>  actions;
    private List<String> convert;
    private List<String> mime;

}
