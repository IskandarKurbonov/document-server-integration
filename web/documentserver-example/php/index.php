<?php // phpcs:ignore PSR1.Files.SideEffects.FoundWithSymbols

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

namespace Example;

require_once __DIR__ . '/vendor/autoload.php';
require_once __DIR__ . '/src/ajax.php';
require_once __DIR__ . '/src/functions.php';
require_once __DIR__ . '/src/trackmanager.php';

use Example\Common\HTTPStatus;
use Example\Common\URL;
use Example\Configuration\ConfigurationManager;
use Example\Views\DocEditorView;
use Example\Views\IndexView;

function configure()
{
    $configManager = new ConfigurationManager();
    if ($configManager->sslVerifyPeerModeEnabled()) {
        // Ignore self-signed certificate.
        stream_context_set_default([
            'ssl' => [
                'verify_peer' => false,
                'verify_peer_name' => false
            ]
        ]);
    }
}

function routers()
{
    // TODO: delete fallback.
    // In theory, the content type of the response should be declared inside the
    // router function. However, this statement isn't true for all routers, and
    // it's also not true for all branches in all routers. Therefore, we are
    // setting the default content type for all routers here.
    header('Content-Type: application/json; charset=utf-8');

    header('Cache-Control: no-cache, must-revalidate, max-age=0');
    header('Expires: Wed, 11 Jan 1984 05:00:00 GMT');
    header('Pragma: no-cache');
    @header_remove('Last-Modified');
    header('X-Content-Type-Options: nosniff');
    header('X-Robots-Tag: noindex');

    $url = new URL($_SERVER['REQUEST_URI']);
    sendlog($url->string(), 'webedior-ajax.log');

    $path = $url->path();
    if (!$path || $path === '/') {
        header('Content-Type: text/html; charset=utf-8');
        $view = new IndexView($_REQUEST);
        $view->render();
        return;
    }
    if (str_starts_with($path, '/editor')) {
        header('Content-Type: text/html; charset=utf-8');
        $view = new DocEditorView($_REQUEST);
        $view->render();
        return;
    }
    if (str_starts_with($path, '/convert')) {
        $response = convert();
        $response['status'] = 'success';
        echo json_encode($response);
        return;
    }
    if (str_starts_with($path, '/delete')) {
        $response = delete();
        $response['status'] = isset($response['error']) ? 'error' : 'success';
        echo json_encode($response);
        return;
    }
    if (str_starts_with($path, '/download')) {
        $response = download();
        $response['status'] = 'success';
        echo json_encode($response);
        return;
    }
    if (str_starts_with($path, '/files')) {
        $response = files();
        echo json_encode($response);
        return;
    }
    if (str_starts_with($path, '/history')) {
        $response = historyDownload();
        $response['status'] = 'success';
        echo json_encode($response);
        return;
    }
    if (str_starts_with($path, '/objhistory')) {
        $response = historyObj();
        echo json_encode($response);
        return;
    }
    if (str_starts_with($path, '/reference')) {
        $response = reference();
        $response['status'] = 'success';
        echo json_encode($response);
        return;
    }
    if (str_starts_with($path, '/rename')) {
        $response = renamefile();
        $content = json_encode($response);
        echo $content;
        return;
    }
    if (str_starts_with($path, '/restore')) {
        $response = restore();
        echo json_encode($response);
        return;
    }
    if (str_starts_with($path, '/saveas')) {
        $response = saveas();
        $response['status'] = 'success';
        echo json_encode($response);
        return;
    }
    if (str_starts_with($path, '/track')) {
        $response = track();
        $response['status'] = 'success';
        echo json_encode($response);
        return;
    }
    if (str_starts_with($path, '/upload')) {
        $response = upload();
        $response['status'] = isset($response['error']) ? 'error' : 'success';
        echo json_encode($response);
        return;
    }

    http_response_code(HTTPStatus::NotFound->value);
}

configure();
routers();
