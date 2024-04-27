// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package helpers;

import entities.FileType;
import format.Format;
import format.FormatManager;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FileUtility {
    private static FormatManager formatManager = new FormatManager();

    private FileUtility() { }

    // get file type
    public static FileType getFileType(final String fileName) {
        String ext = getFileExtension(fileName).toLowerCase();
        List<Format> formats  = FileUtility.formatManager.getFormats();

        for (Format format : formats) {
            if (format.getName().equals(ext)) {
                return format.getType();
            }
        }

        // default file type is word
        return FileType.WORD;
    }

    // get file name from the url
    public static String getFileName(final String url) {
        if (url == null) {
            return "";
        }

        // get file name from the last part of url
        String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
        fileName = fileName.split("\\?")[0];
        return fileName;
    }

    // get file name without extension
    public static String getFileNameWithoutExtension(final String url) {
        String fileName = getFileName(url);
        if (fileName == null) {
            return null;
        }
        String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
        return fileNameWithoutExt;
    }

    // get file extension from url
    public static String getFileExtension(final String url) {
        String fileName = getFileName(url);
        if (fileName == null) {
            return null;
        }
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileExt.toLowerCase();
    }

    // get url parameters
    public static Map<String, String> getUrlParams(final String url) {
        try {
            // take all the parameters which are placed after ? sign in the file url
            String query = new URL(url).getQuery();
            String[] params = query.split("&");  // parameters are separated by & sign
            Map<String, String> map = new HashMap<>();
            for (String param : params) {  // write parameters and their values to the map dictionary
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                map.put(name, value);
            }
            return map;
        } catch (Exception ex) {
            return null;
        }
    }
}
