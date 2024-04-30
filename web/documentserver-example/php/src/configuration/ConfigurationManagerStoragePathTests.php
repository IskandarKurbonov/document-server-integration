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

final class ConfigurationManagerStoragePathTests extends TestCase
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
        $path = $configManager->storagePath();
        $this->assertTrue($path->absolute());
        $this->assertEquals($path->basename(), 'storage');
    }

    public function testAssignsARelativePathFromTheEnvironment()
    {
        putenv('STORAGE_PATH=directory');
        $configManager = new ConfigurationManager();
        $path = $configManager->storagePath();
        $this->assertTrue($path->absolute());
        $this->assertEquals($path->basename(), 'directory');
    }

    public function testAssignsAnAbsolutePathFromTheEnvironment()
    {
        putenv('STORAGE_PATH=/directory');
        $configManager = new ConfigurationManager();
        $path = $configManager->storagePath();
        $this->assertEquals($path->string(), '/directory');
    }
}
