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

namespace Example\Format;

final class Format
{
    public string $name;

    public string $type;

    /**
     * @var string[]
     */
    public array $actions;

    /**
     * @var string[]
     */
    public array $convert;

    /**
     * @var string[]
     */
    public array $mime;

    public function extension(): string
    {
        // In PHP, unlike other languages, when parsing a path, the extension is
        // returned without a dot.
        //
        // ```php
        // pathinfo('file.docx', PATHINFO_EXTENSION) === 'docx'
        // ```
        return $this->name;
    }
}
