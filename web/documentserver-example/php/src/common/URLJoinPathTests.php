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

final class URLJoinPathTests extends TestCase
{
    public function testJoinsARelativeToAnEmptyOne()
    {
        $url = new URL('http://localhost');
        $joined = $url->joinPath('first');
        $this->assertEquals('http', $joined->scheme());
        $this->assertEquals('localhost', $joined->host());
        $this->assertEquals(null, $joined->port());
        $this->assertEquals(null, $joined->user());
        $this->assertEquals(null, $joined->pass());
        $this->assertEquals('/first', $joined->path());
        $this->assertEquals(null, $joined->query());
        $this->assertEquals(null, $joined->fragment());
    }

    public function testJoinsARelative()
    {
        $url = new URL('http://localhost/first');
        $joined = $url->joinPath('second');
        $this->assertEquals('http', $joined->scheme());
        $this->assertEquals('localhost', $joined->host());
        $this->assertEquals(null, $joined->port());
        $this->assertEquals(null, $joined->user());
        $this->assertEquals(null, $joined->pass());
        $this->assertEquals('/first/second', $joined->path());
        $this->assertEquals(null, $joined->query());
        $this->assertEquals(null, $joined->fragment());
    }

    public function testJoinsAnAbsoluteToAnEmptyOne()
    {
        $url = new URL('http://localhost');
        $joined = $url->joinPath('/first');
        $this->assertEquals('http', $joined->scheme());
        $this->assertEquals('localhost', $joined->host());
        $this->assertEquals(null, $joined->port());
        $this->assertEquals(null, $joined->user());
        $this->assertEquals(null, $joined->pass());
        $this->assertEquals('/first', $joined->path());
        $this->assertEquals(null, $joined->query());
        $this->assertEquals(null, $joined->fragment());
    }

    public function testJoinsAnAbsolute()
    {
        $url = new URL('http://localhost/first');
        $joined = $url->joinPath('/second');
        $this->assertEquals('http', $joined->scheme());
        $this->assertEquals('localhost', $joined->host());
        $this->assertEquals(null, $joined->port());
        $this->assertEquals(null, $joined->user());
        $this->assertEquals(null, $joined->pass());
        $this->assertEquals('/first/second', $joined->path());
        $this->assertEquals(null, $joined->query());
        $this->assertEquals(null, $joined->fragment());
    }
}
