// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntegrationApplication {

    // run the SpringApplication from the IntagrationApplication with the specified parameters
    public static void main(final String[] args) {
        SpringApplication.run(IntegrationApplication.class, args);
    }

}
