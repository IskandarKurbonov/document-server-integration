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

use Example\Configuration\ConfigurationManager;
use Example\Format\FormatManager;
use Example\Helpers\ExampleUsers;
use function Example\getStoredFiles;

final class IndexView extends View
{

    public function __construct($request, $tempName = "index")
    {
        parent::__construct($tempName);
        $formatManager = new FormatManager();
        $configManager = new ConfigurationManager();

        $storedList = new IndexStoredListView($request);
        $portalInfo = $this->getPortalInfoStyleDisplay();

        $this->tagsValues = [
            "user" => isset($request["user"]) ? htmlentities($request["user"]) : "",
            "userOpts" => $this->getUserListOptionsLayout(),
            "langs" => $this->getLanguageListOptionsLayout(),
            "portalInfoDisplay" => $portalInfo,
            "userDescr" => $this->getUserDescriptionLayout(),
            "storedList" => $portalInfo == "none" ? $storedList->getParsedTemplate() : "",
            "editButton" => $this->getEditButton(),
            "dataDocs" => $this->getPreloaderUrl(),
            "date" => date("Y"),
            "fillFormsExtList" => implode(",", $formatManager->fillableExtensions()),
            "converExtList" => implode(",", $formatManager->convertibleExtensions()),
            "editedExtList" => implode(",", $formatManager->editableExtensions()),
            "serverVersion" => $configManager -> getVersion(),
        ];
    }

    private function getUserListOptionsLayout()
    {
        $layout = "";
        $userList = new ExampleUsers();
        foreach ($userList->getAllUsers() as $userL) {
            $name = $userL->name ?: "Anonymous";
            $layout .= '<option value="'.$userL->id.'">'.$name.'</option>'.PHP_EOL;
        }
        return $layout;
    }

    private function getLanguageListOptionsLayout()
    {
        $layout = "";
        $configManager = new ConfigurationManager();
        foreach ($configManager->languages() as $key => $language) {
            $layout .= '<option value="'.$key.'">'.$language.'</option>'.PHP_EOL;
        }
        return $layout;
    }

    private function getPortalInfoStyleDisplay()
    {
        $storedFiles = getStoredFiles();
        if (!empty($storedFiles)) {
            return "none";
        }
        return "table-cell";
    }

    private function getUserDescriptionLayout()
    {
        $layout = "";
        $userList = new ExampleUsers();
        foreach ($userList->getAllUsers() as $userL) {
            $name = $userL->name ?: "Anonymous";
            $layout .= '<div class="user-descr"><br><b>'.$name.'</b><br><ul>';
            foreach ($userL->descriptions as $description) {
                $layout .= '<li>'.$description.'</li>';
            }
            $layout .= '</ul><br></div>';
        }
        return $layout;
    }

    private function getStoredListLayout()
    {
        $storedList = new IndexStoredListView();
        return $storedList->getParsedTemplate();
    }

    private function getPreloaderUrl()
    {
        $configManager = new ConfigurationManager();
        return $configManager->documentServerPreloaderURL()->string();
    }

    private function getEditButton()
    {
        return '<div id="beginEdit" class="button orange disable">Edit</div>';
    }
}
