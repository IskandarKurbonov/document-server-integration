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

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class FormatManager {

    private List<Format> formats;

    public FormatManager() {
        formats = this.all();
    }

    public List<Format> getFormats() {
        return this.formats;
    }

    public List<Format> getFormatsByAction(final String action) {
        return this
                .all()
                .stream()
                .filter(format -> format.getActions().contains(action))
                .collect(Collectors.toList());
    }

    public List<String> allExtensions() {
        return this
                .formats
                .stream()
                .map(format -> format.getName())
                .collect(Collectors.toList());
    }

    public List<String> fillableExtensions() {
        return this
            .getFormatsByAction("fill")
            .stream()
            .map(format -> format.getName())
            .collect(Collectors.toList());
    }

    public List<String> viewableExtensions() {
        return this
            .getFormatsByAction("view")
            .stream()
            .map(format -> format.getName())
            .collect(Collectors.toList());
    }

    public List<String> editableExtensions() {
        return Stream.of(this.getFormatsByAction("edit"), this.getFormatsByAction("lossy-edit"))
            .flatMap(x -> x.stream())
            .map(format -> format.getName())
            .collect(Collectors.toList());
    }

    public List<String> autoConvertExtensions() {
        return this
                .getFormatsByAction("auto-convert")
                .stream()
                .map(format -> format.getName())
                .collect(Collectors.toList());
    }

    private List<Format> all()  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            Path path = this.file();
            return objectMapper.readValue(Files.readAllBytes(path), new TypeReference<List<Format>>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private Path file() throws URISyntaxException {
        return this
            .directory()
            .resolve("onlyoffice-docs-formats.json");
    }

    private Path directory() throws URISyntaxException {
        URI uri = Thread
            .currentThread()
            .getContextClassLoader()
            .getResource("assets/document-formats")
            .toURI();
        return Paths.get(uri);
    }
}
