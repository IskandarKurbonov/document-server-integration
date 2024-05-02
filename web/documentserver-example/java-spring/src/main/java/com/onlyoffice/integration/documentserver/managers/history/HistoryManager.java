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

package com.onlyoffice.integration.documentserver.managers.history;

// specify the history manager functions
public interface HistoryManager {
    String getHistory(String fileName);  // get document history

    String getHistoryData(String fileName, String version, Boolean directUrl); // get document history data
}
