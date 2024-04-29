<?php

//  Copyright Ascensio System SIA 2024
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

namespace Example\Configuration\Tests;

use PHPUnit\Framework\TestCase;
use Example\Configuration\ConfigurationManager;

final class ConfigurationManagerJWTSecretTests extends TestCase
{
    public array $env;

    public function __construct(string $name)
    {
        $this->env = getenv();
        parent::__construct($name);
    }

    protected function setUp(): void
    {
        foreach ($this->env as $key => $value) {
            putenv("{$key}={$value}");
        }
    }

    public function testAssignsADefaultValue()
    {
        $configManager = new ConfigurationManager();
        $secret = $configManager->jwtSecret();
        $this->assertEquals('', $secret);
    }

    public function testAssignsAValueFromTheEnvironment()
    {
        putenv('JWT_SECRET=your-256-bit-secret');
        $configManager = new ConfigurationManager();
        $secret = $configManager->jwtSecret();
        $this->assertEquals('your-256-bit-secret', $secret);
    }
}
