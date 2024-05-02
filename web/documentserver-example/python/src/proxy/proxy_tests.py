#  Copyright Ascensio System SIA 2024
#  SPDX-FileCopyrightText: 2024 Ascensio System SIA
#
#  SPDX-License-Identifier: Ascensio-System
#
#     Our License onlyoffice.com
#     Empty line
#     Empty line
#     Empty line
#     Empty line
#     Empty line
#     Empty line
#     Empty line
#     

from unittest import TestCase
from unittest.mock import patch
from urllib.parse import urlparse
from src.configuration import ConfigurationManager
from . import ProxyManager


class ProxyManagerTests(TestCase):
    @patch.object(
        ConfigurationManager,
        'document_server_public_url',
        return_value=urlparse('http://localhost')
    )
    @patch.object(
        ConfigurationManager,
        'document_server_private_url',
        return_value=urlparse('http://proxy')
    )
    def test_resolves_a_url_that_refers_to_the_public_url(self, *_):
        config_manager = ConfigurationManager()
        proxy_manager = ProxyManager(config_manager)

        raw_url = 'http://localhost/endpoint?query=string'
        url = urlparse(raw_url)
        resolved_url = proxy_manager.resolve_url(url)

        self.assertEqual(
            resolved_url.geturl(),
            'http://proxy/endpoint?query=string'
        )

    @patch.object(
        ConfigurationManager,
        'document_server_public_url',
        return_value=urlparse('http://localhost')
    )
    def test_resolves_a_url_that_does_not_refers_to_the_public_url(self, *_):
        config_manager = ConfigurationManager()
        proxy_manager = ProxyManager(config_manager)

        raw_url = 'http://proxy/endpoint?query=string'
        url = urlparse(raw_url)
        resolved_url = proxy_manager.resolve_url(url)

        self.assertEqual(
            resolved_url.geturl(),
            'http://proxy/endpoint?query=string'
        )
