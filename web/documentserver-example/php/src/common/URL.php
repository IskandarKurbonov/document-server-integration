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

namespace Example\Common;

final class URL
{
    private string $string;

    public function __construct(string $url)
    {
        $this->string = $url;
    }

    public function string(): string
    {
        return $this->string;
    }

    public function scheme(): ?string
    {
        $string = $this->string();
        return parse_url($string, PHP_URL_SCHEME) ?: null;
    }

    public function host(): ?string
    {
        $string = $this->string();
        return parse_url($string, PHP_URL_HOST) ?: null;
    }

    public function port(): ?int
    {
        $string = $this->string();
        return parse_url($string, PHP_URL_PORT) ?: null;
    }

    public function user(): ?string
    {
        $string = $this->string();
        return parse_url($string, PHP_URL_USER) ?: null;
    }

    public function pass(): ?string
    {
        $string = $this->string();
        return parse_url($string, PHP_URL_PASS) ?: null;
    }

    public function path(): ?string
    {
        $string = $this->string();
        return parse_url($string, PHP_URL_PATH) ?: null;
    }

    public function query(): ?string
    {
        $string = $this->string();
        return parse_url($string, PHP_URL_QUERY) ?: null;
    }

    public function fragment(): ?string
    {
        $string = $this->string();
        return parse_url($string, PHP_URL_FRAGMENT) ?: null;
    }

    public static function fromComponents(
        ?string $scheme,
        ?string $host,
        ?int $port,
        ?string $user,
        ?string $pass,
        ?string $path,
        ?string $query,
        ?string $fragment
    ): URL {
        $string = '';
        if ($scheme) {
            $string .= "{$scheme}://";
        }
        if ($user) {
            $string .= $user;
        }
        if ($pass) {
            $string .= ":{$pass}@";
        }
        if ($host) {
            $string .= $host;
        }
        if ($port) {
            $string .= ":{$port}";
        }
        if ($path) {
            $string .= $path;
        }
        if ($query) {
            $string .= "?{$query}";
        }
        if ($fragment) {
            $string .= "#{$fragment}";
        }
        return new URL($string);
    }

    public function joinPath(string $path): self
    {
        $currentPath = $this->path();
        $separator =
            $currentPath &&
            (
                str_ends_with($currentPath, '/') ||
                str_starts_with($path, '/')
            ) ||
            !$currentPath && str_starts_with($path, '/')
            ? ''
            : '/';
        $separated = "{$currentPath}{$separator}{$path}";
        return URL::fromComponents(
            $this->scheme(),
            $this->host(),
            $this->port(),
            $this->user(),
            $this->pass(),
            $separated,
            $this->query(),
            $this->fragment()
        );
    }
}
