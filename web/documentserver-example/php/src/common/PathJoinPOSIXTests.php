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
use Example\Common\Path;

final class PathJoinPOSIXTests extends TestCase
{
    public function testJoinsARelativeToAnEmptyOne()
    {
        $path = new Path('');
        $joined = $path->joinPath('srv');
        $this->assertEquals($joined->dirname(), '/');
        $this->assertEquals($joined->basename(), 'srv');
        $this->assertEquals($joined->extension(), null);
        $this->assertEquals($joined->filename(), 'srv');
    }

    public function testJoinsARelativeToARelativeOne()
    {
        $path = new Path('.');
        $joined = $path->joinPath('srv');
        $this->assertEquals($joined->dirname(), '.');
        $this->assertEquals($joined->basename(), 'srv');
        $this->assertEquals($joined->extension(), null);
        $this->assertEquals($joined->filename(), 'srv');
    }

    public function testJoinsARelativeToAnAbsoluteOne()
    {
        $path = new Path('/');
        $joined = $path->joinPath('srv');
        $this->assertEquals($joined->dirname(), '/');
        $this->assertEquals($joined->basename(), 'srv');
        $this->assertEquals($joined->extension(), null);
        $this->assertEquals($joined->filename(), 'srv');
    }

    public function testJoinsAnAbsoluteToAnEmptyOne()
    {
        $path = new Path('');
        $joined = $path->joinPath('/srv');
        $this->assertEquals($joined->dirname(), '/');
        $this->assertEquals($joined->basename(), 'srv');
        $this->assertEquals($joined->extension(), null);
        $this->assertEquals($joined->filename(), 'srv');
    }

    public function testJoinsAnAbsoluteToARelativeOne()
    {
        $path = new Path('.');
        $joined = $path->joinPath('/srv');
        $this->assertEquals($joined->dirname(), '.');
        $this->assertEquals($joined->basename(), 'srv');
        $this->assertEquals($joined->extension(), null);
        $this->assertEquals($joined->filename(), 'srv');
    }

    public function testJoinsAnAbsoluteToAnAbsoluteOne()
    {
        $path = new Path('/');
        $joined = $path->joinPath('/srv');
        $this->assertEquals($joined->dirname(), '/');
        $this->assertEquals($joined->basename(), 'srv');
        $this->assertEquals($joined->extension(), null);
        $this->assertEquals($joined->filename(), 'srv');
    }

    public function testJoinsAnUnnormalized()
    {
        $path = new Path('');
        $joined = $path->joinPath('../srv');
        $this->assertEquals($joined->dirname(), '/..');
        $this->assertEquals($joined->basename(), 'srv');
        $this->assertEquals($joined->extension(), null);
        $this->assertEquals($joined->filename(), 'srv');
    }
}
