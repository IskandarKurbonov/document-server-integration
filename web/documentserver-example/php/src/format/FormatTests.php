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
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Example\Format\Format;

final class FormatTests extends TestCase
{
    public string $json =
    '
    {
      "name": "djvu",
      "type": "word",
      "actions": ["view"],
      "convert": ["bmp", "gif", "jpg", "pdf", "pdfa", "png"],
      "mime": ["image/vnd.djvu"]
    }
    ';

    public function testGeneratesExtension()
    {
        $serializer = new Serializer(
            [
                new ObjectNormalizer()
            ],
            [
                new JsonEncoder()
            ]
        );
        $format = $serializer->deserialize($this->json, Format::class, 'json');
        $this->assertEquals('djvu', $format->extension());
    }
}
