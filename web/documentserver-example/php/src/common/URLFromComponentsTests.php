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

namespace Example\Common\Tests;

use PHPUnit\Framework\TestCase;
use Example\Common\URL;

final class URLFromComponentsTests extends TestCase
{
    public function testCreates()
    {
        $url = URL::fromComponents(
            'http',
            'localhost',
            8080,
            'user',
            'password',
            '/path',
            'q=value',
            'fragment'
        );
        $this->assertEquals('http', $url->scheme());
        $this->assertEquals('localhost', $url->host());
        $this->assertEquals(8080, $url->port());
        $this->assertEquals('user', $url->user());
        $this->assertEquals('password', $url->pass());
        $this->assertEquals('/path', $url->path());
        $this->assertEquals('q=value', $url->query());
        $this->assertEquals('fragment', $url->fragment());
    }
}
