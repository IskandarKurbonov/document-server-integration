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

namespace Example\Format\Tests;

use PHPUnit\Framework\TestCase;
use Example\Format\FormatManager;

final class FormatManagerFillableTests extends TestCase
{
    public function testLoads()
    {
        $formatManager = new FormatManager();
        $formats = $formatManager->editable();
        $this->assertNotEmpty($formats);
    }
}
