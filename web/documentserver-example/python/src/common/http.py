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

# TODO: add types for parameters.
# https://github.com/python/typing/discussions/946r

from http import HTTPStatus, HTTPMethod
from django.http import HttpRequest, HttpResponse


def GET():
    return method(HTTPMethod.GET)


def POST():
    return method(HTTPMethod.POST)


def PUT():
    return method(HTTPMethod.PUT)


def method(meth: HTTPMethod):
    def wrapper(func):
        def inner(request: HttpRequest, *args, **kwargs):
            if request.method is None:
                return HttpResponse(
                    status=HTTPStatus.METHOD_NOT_ALLOWED
                )

            if request.method.upper() != meth.name:
                return HttpResponse(
                    status=HTTPStatus.METHOD_NOT_ALLOWED
                )

            return func(request, *args, **kwargs)

        return inner

    return wrapper
