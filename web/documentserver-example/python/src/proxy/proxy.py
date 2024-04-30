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

from urllib.parse import ParseResult
from src.configuration import ConfigurationManager


class ProxyManager():
    config_manager: ConfigurationManager

    def __init__(self, config_manager: ConfigurationManager):
        self.config_manager = config_manager

    def resolve_url(self, url: ParseResult) -> ParseResult:
        if not self.__refer_public_url(url):
            return url
        return self.__redirect_public_url(url)

    def __refer_public_url(self, url: ParseResult) -> bool:
        public_url = self.config_manager.document_server_public_url()
        return (
            url.scheme == public_url.scheme and
            url.hostname == public_url.hostname and
            url.port == public_url.port
        )

    def __redirect_public_url(self, url: ParseResult) -> ParseResult:
        private_url = self.config_manager.document_server_private_url()
        return ParseResult(
            scheme=private_url.scheme,
            netloc=private_url.netloc,
            path=url.path,
            params=url.params,
            query=url.query,
            fragment=url.fragment
        )
