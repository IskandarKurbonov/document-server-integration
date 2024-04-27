// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package helpers;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {
    private static Properties properties;

    private ConfigManager() { }

    static {
        init();
    }

    private static void init() {
        try {
            // get stream from the settings.properties resource and load it
            properties = new Properties();
            InputStream stream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("settings.properties");
            properties.load(stream);
        } catch (Exception ex) {
            properties = null;
        }
    }

    // get name from the settings.properties file
    public static String getProperty(final String name) {
        if (properties == null) {
            return "";
        }

        // get property by its name
        String property = properties.getProperty(name);

        return property == null ? "" : property;
    }
}
