// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.managers.history;

// specify the history manager functions
public interface HistoryManager {
    String getHistory(String fileName);  // get document history

    String getHistoryData(String fileName, String version, Boolean directUrl); // get document history data
}
