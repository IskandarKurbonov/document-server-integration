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

namespace Example\Proxy\Tests;

use Example\Common\URL;
use PHPUnit\Framework\TestCase;
use Example\Configuration\ConfigurationManager;
use Example\Proxy\ProxyManager;

final class ProxyManagerTests extends TestCase
{
    public function testResolvesAURLThatRefersToThePublicURL()
    {
        $configManager = $this->createStub(ConfigurationManager::class);
        $configManager
            ->method('documentServerPublicURL')
            ->willReturn(new URL('http://localhost'));
        $configManager
            ->method('documentServerPrivateURL')
            ->willReturn(new URL('http://proxy'));

        $proxyManager = new ProxyManager($configManager);

        $rawURL = 'http://localhost/endpoint?query=string';
        $url = new URL($rawURL);
        $resolvedURL = $proxyManager->resolveURL($url);

        $this->assertEquals(
            'http://proxy/endpoint?query=string',
            $resolvedURL->string()
        );
    }

    public function testResolvesAURLThatDoesNotRefersToThePublicURL()
    {
        $configManager = $this->createStub(ConfigurationManager::class);
        $configManager
            ->method('documentServerPublicURL')
            ->willReturn(new URL('http://localhost'));

        $proxyManager = new ProxyManager($configManager);

        $rawURL = 'http://proxy/endpoint?query=string';
        $url = new URL($rawURL);
        $resolvedURL = $proxyManager->resolveURL($url);

        $this->assertEquals(
            'http://proxy/endpoint?query=string',
            $resolvedURL->string()
        );
    }
}
