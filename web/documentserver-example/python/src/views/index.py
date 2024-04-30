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

import json

from django.shortcuts import render

from src.configuration import ConfigurationManager
from src.format import FormatManager
from src.utils import users
from src.utils import docManager

config_manager = ConfigurationManager()
format_manager = FormatManager()


def getDirectUrlParam(request):
    if 'directUrl' in request.GET:
        return request.GET['directUrl'].lower() in ("true")

    return False


def default(request):  # default parameters that will be passed to the template
    context = {
        'users': users.USERS,
        'languages': config_manager.languages(),
        'preloadurl': config_manager.document_server_preloader_url().geturl(),
        'editExt': json.dumps(format_manager.editable_extensions()),  # file extensions that can be edited
        'convExt': json.dumps(format_manager.convertible_extensions()),  # file extensions that can be converted
        'files': docManager.getStoredFiles(request),  # information about stored files
        'fillExt': json.dumps(format_manager.fillable_extensions()),
        'directUrl': str(getDirectUrlParam(request)).lower,
        'serverVersion': config_manager.getVersion()
    }
    # execute the "index.html" template with context data and return http response in json format
    return render(request, 'index.html', context)
