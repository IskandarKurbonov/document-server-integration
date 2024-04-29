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

namespace Example\Proxy;

use Example\Common\URL;
use Example\Configuration\ConfigurationManager;

class ProxyManager
{
    public ConfigurationManager $configManager;

    public function __construct(ConfigurationManager $configManager)
    {
        $this->configManager = $configManager;
    }

    public function resolveURL(URL $url): URL
    {
        if (!$this->referPublicURL($url)) {
            return $url;
        }
        return $this->redirectPublicURL($url);
    }

    private function referPublicURL(URL $url): bool
    {
        $publicURL = $this->configManager->documentServerPublicURL();
        return
            $url->scheme() == $publicURL->scheme() &&
            $url->host() == $publicURL->host() &&
            $url->port() == $publicURL->port();
    }

    private function redirectPublicURL(URL $url): URL
    {
        $privateURL = $this->configManager->documentServerPrivateURL();
        return URL::fromComponents(
            $privateURL->scheme(),
            $privateURL->host(),
            $privateURL->port(),
            $url->user(),
            $url->pass(),
            $url->path(),
            $url->query(),
            $url->fragment()
        );
    }
}
