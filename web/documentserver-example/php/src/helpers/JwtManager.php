<?php

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

namespace Example\Helpers;



use Firebase\JWT\JWT;
use Firebase\JWT\Key;
use Example\Configuration\ConfigurationManager;

final class JwtManager
{
    /**
     * Check if a secret key to generate token exists or not.
     *
     * @return bool
     */
    public function isJwtEnabled(): bool
    {
        $configManager = new ConfigurationManager();
        return !empty($configManager->jwtSecret());
    }

    /**
     * Check if a secret key use for request
     *
     * @return bool
     */
    public function tokenUseForRequest(): bool
    {
        $configManager = new ConfigurationManager();
        return $configManager->jwtUseForRequest() ?: false;
    }

    /**
     * Encode a payload object into a token using a secret key
     *
     * @param array $payload
     *
     * @return string
     */
    public function jwtEncode($payload)
    {
        $configManager = new ConfigurationManager();
        return JWT::encode($payload, $configManager->jwtSecret(), 'HS256');
    }

    /**
     * Decode a token into a payload object using a secret key
     *
     * @param string $token
     *
     * @return string
     */
    public function jwtDecode($token)
    {
        $configManager = new ConfigurationManager();
        try {
            $payload = JWT::decode(
                $token,
                new Key($configManager->jwtSecret(), 'HS256')
            );
        } catch (\UnexpectedValueException $e) {
            $payload = "";
        }

        return $payload;
    }
}
