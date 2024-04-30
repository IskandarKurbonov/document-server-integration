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

from http import HTTPMethod, HTTPStatus
from unittest import TestCase
from django.conf import settings
from django.http import HttpRequest, HttpResponse
from . import http

# Under the hood, HttpRequest uses a settings object.
settings.configure()


@http.GET()
def endpoint(_: HttpRequest) -> HttpResponse:
    return HttpResponse()


class HTTPMethodTests(TestCase):
    def test_returns_a_response_from_the_endpoint(self):
        request = HttpRequest()
        request.method = HTTPMethod.GET
        response = endpoint(request)
        self.assertEqual(response.status_code, HTTPStatus.OK)

    def test_returns_an_error_response(self):
        request = HttpRequest()
        request.method = HTTPMethod.POST
        response = endpoint(request)
        self.assertEqual(response.status_code, HTTPStatus.METHOD_NOT_ALLOWED)
