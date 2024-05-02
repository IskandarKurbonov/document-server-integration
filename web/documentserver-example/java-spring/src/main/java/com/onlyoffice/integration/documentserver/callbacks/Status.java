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

package com.onlyoffice.integration.documentserver.callbacks;

// document status
public enum Status {
    EDITING(1),  // 1 - document is being edited
    SAVE(2),  // 2 - document is ready for saving
    CORRUPTED(3),  // 3 - document saving error has occurred
    MUST_FORCE_SAVE(6),  // 6 - document is being edited, but the current document state is saved
    CORRUPTED_FORCE_SAVE(7);  // 7 - error has occurred while force saving the document
    private int code;
    Status(final int codeParam) {
        this.code = codeParam;
    }
    public int getCode() {  // get document status
        return this.code;
    }
}
