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

from http import HTTPStatus
from json import dumps
from django.http import HttpResponse


class ErrorResponse(HttpResponse):
    def __init__(self, message: str, status: HTTPStatus):
        payload = {
            'error': message,
            'success': False
        }
        content = dumps(payload)
        super().__init__(
            content,
            content_type='application/json',
            status=status
        )
