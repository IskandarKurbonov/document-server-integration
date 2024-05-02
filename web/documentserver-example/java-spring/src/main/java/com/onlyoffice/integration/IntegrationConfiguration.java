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

package com.onlyoffice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyoffice.integration.documentserver.storage.FileStoragePathBuilder;
import org.json.simple.parser.JSONParser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import com.onlyoffice.integration.documentserver.util.SSLUtils;

@Configuration
public class IntegrationConfiguration {

    @Value("${files.storage}")
    private String storageAddress;

    @Value("${files.docservice.verify-peer-off}")
    private String verifyPerrOff;

    @Autowired
    private FileStoragePathBuilder storagePathBuilder;

    @Autowired
    private SSLUtils ssl;

    @Bean
    public ModelMapper mapper() {  // create the model mapper
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()  // get the mapper configuration and set new parameters to it
                .setMatchingStrategy(MatchingStrategies.STRICT)  // specify the STRICT matching strategy
                .setFieldMatchingEnabled(true)  // define if the field matching is enabled or not
                .setSkipNullEnabled(true)  // define if null value will be skipped or not
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);  /* specify
                 the PRIVATE field access level */
        return mapper;
    }

    @Bean
    public JSONParser jsonParser() {  // create JSON parser
        return new JSONParser();
    }

    @PostConstruct
    public void init() {  // initialize the storage path builder
        storagePathBuilder.configure(storageAddress.isBlank() ? null : storageAddress);
        if (!verifyPerrOff.isEmpty()) {
            try {
                if (verifyPerrOff.equals("true")) {
                    ssl.turnOffSslChecking(); //the certificate will be ignored
                } else {
                    ssl.turnOnSslChecking(); //the certificate will be verified
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Bean
    public ObjectMapper objectMapper() {  // create the object mapper
        return new ObjectMapper();
    }
}
