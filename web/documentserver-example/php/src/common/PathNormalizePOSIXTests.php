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

final class PathNormalizePOSIXTests extends TestCase
{
    public function testNormalizes()
    {
        $path = new Path('./srv///sub/.././sub/file.docx');
        $normalized = $path->normalize();
        $this->assertEquals($normalized->dirname(), 'srv/sub');
        $this->assertEquals($normalized->basename(), 'file.docx');
        $this->assertEquals($normalized->extension(), 'docx');
        $this->assertEquals($normalized->filename(), 'file');
    }
}
