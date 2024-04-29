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

final class URLStringTests extends TestCase
{
    public function testGenerates()
    {
        $url = new URL('http://user:password@localhost:8080/path?q=value#fragment');
        $string = $url->string();
        $this->assertEquals(
            'http://user:password@localhost:8080/path?q=value#fragment',
            $string
        );
    }
}
