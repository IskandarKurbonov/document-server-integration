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

use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ArrayDenormalizer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Example\Common\Path;

class FormatManager
{
    private Serializer $serializer;

    public function __construct()
    {
        $this->serializer = new Serializer(
            [
                new ArrayDenormalizer(),
                new ObjectNormalizer()
            ],
            [
                new JsonEncoder()
            ]
        );
    }

    /**
     * @return string[]
     */
    public function fillableExtensions(): array
    {
        $formats = $this->fillable();
        $extensions = [];
        foreach ($formats as $format) {
            $extensions[] = $format->extension();
        }
        return $extensions;
    }

    /**
     * @return Format[]
     */
    public function fillable(): array
    {
        $formats = $this->all();
        $filtered = [];
        foreach ($formats as $format) {
            if (in_array('fill', $format->actions)) {
                $filtered[] = $format;
            }
        }
        return $filtered;
    }

    /**
     * @return string[]
     */
    public function viewableExtensions(): array
    {
        $formats = $this->viewable();
        $extensions = [];
        foreach ($formats as $format) {
            $extensions[] = $format->extension();
        }
        return $extensions;
    }

    /**
     * @return Format[]
     */
    public function viewable(): array
    {
        $formats = $this->all();
        $filtered = [];
        foreach ($formats as $format) {
            if (in_array('view', $format->actions)) {
                $filtered[] = $format;
            }
        }
        return $filtered;
    }

    /**
     * @return string[]
     */
    public function editableExtensions(): array
    {
        $formats = $this->editable();
        $extensions = [];
        foreach ($formats as $format) {
            $extensions[] = $format->extension();
        }
        return $extensions;
    }

    /**
     * @return Format[]
     */
    public function editable(): array
    {
        $formats = $this->all();
        $filtered = [];
        foreach ($formats as $format) {
            if (in_array('edit', $format->actions) or
                in_array('lossy-edit', $format->actions)
            ) {
                $filtered[] = $format;
            }
        }
        return $filtered;
    }

    /**
     * @return string[]
     */
    public function convertibleExtensions(): array
    {
        $formats = $this->convertible();
        $extensions = [];
        foreach ($formats as $format) {
            $extensions[] = $format->extension();
        }
        return $extensions;
    }

    /**
     * @return Format[]
     */
    public function convertible(): array
    {
        $formats = $this->all();
        $filtered = [];
        foreach ($formats as $format) {
            if (in_array('auto-convert', $format->actions)) {
                $filtered[] = $format;
            }
        }
        return $filtered;
    }

    /**
     * @return string[]
     */
    public function allExtensions(): array
    {
        $formats = $this->all();
        $extensions = [];
        foreach ($formats as $format) {
            $extensions[] = $format->extension();
        }
        return $extensions;
    }

    /**
     * @return Format[]
     */
    public function all(): array
    {
        $file = $this->file();
        $contents = $file->contents();
        return $this->serializer->deserialize(
            $contents,
            Format::class . '[]',
            'json'
        );
    }

    private function file(): Path
    {
        $directory = $this->directory();
        return $directory->joinPath('onlyoffice-docs-formats.json');
    }

    private function directory(): Path
    {
        $currentDirectory = new Path(__DIR__);
        return $currentDirectory
            ->joinPath('..')
            ->joinPath('..')
            ->joinPath('assets')
            ->joinPath('document-formats');
    }
}
