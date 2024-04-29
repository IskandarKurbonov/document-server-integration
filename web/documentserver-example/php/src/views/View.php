<?php

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

namespace Example\Views;

class View
{
    private $template;
    protected $tagsValues;

    public function __construct($tempName)
    {
        $pathToTemplate = "./templates/".$tempName.".tpl";
        if (file_exists($pathToTemplate)) {
            $this->template = file_get_contents($pathToTemplate);
        } else {
            $this->template = "";
        }
    }

    private function parseTemplate($tagsValues = []): array|bool|string
    {
        $parsedTemplate = $this->template;
        foreach ($tagsValues as $tag => $value) {
            $parsedTemplate = str_replace("{".$tag."}", $value, $parsedTemplate);
        }
        return $parsedTemplate;
    }

    protected function getParsedTemplate()
    {
        return $this->parseTemplate($this->tagsValues);
    }

    private function renderTemplate($tagsValues)
    {
        echo ($this->parseTemplate($tagsValues));
    }

    public function render()
    {
        $this->renderTemplate($this->tagsValues);
    }
}
