// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.serializers;

import java.util.List;

public class SerializerFilter {
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof List) {
            if (((List<?>) obj).size() == 1 && ((List<?>) obj).get(0) == FilterState.NULL.toString()) {
                return true;
            }
            return false;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
