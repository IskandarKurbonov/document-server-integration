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

final class PathAbsolutePOSIXTests extends TestCase
{
    public function testRecognizesAnEmptyAsANonAbsolute()
    {
        $path = new Path('');
        $absolute = $path->absolute();
        $this->assertFalse($absolute);
    }

    public function testRecognizesARelativeAsANonAbsolute()
    {
        $path = new Path('.');
        $absolute = $path->absolute();
        $this->assertFalse($absolute);
    }

    public function testRecognizesAnAbsoluteAsAnAbsolute()
    {
        $path = new Path('/');
        $absolute = $path->absolute();
        $this->assertTrue($absolute);
    }
}
