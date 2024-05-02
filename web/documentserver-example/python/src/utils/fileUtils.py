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

from src.configuration import ConfigurationManager
from src.format import FormatManager

config_manager = ConfigurationManager()
format_manager = FormatManager()


# get file name from the document url
def getFileName(uri):
    ind = uri.rfind('/')
    return uri[ind+1:]


# get file name without extension from the document url
def getFileNameWithoutExt(uri):
    fn = getFileName(uri)
    ind = fn.rfind('.')
    return fn[:ind]


# get file extension from the document url
def getFileExt(uri):
    fn = getFileName(uri)
    ind = fn.rfind('.')
    return fn[ind:].lower()


# get file type
def getFileType(uri):
    ext = getFileExt(uri)
    if ext in format_manager.document_extensions():
        return 'word'
    if ext in format_manager.spreadsheet_extensions():
        return 'cell'
    if ext in format_manager.presentation_extensions():
        return 'slide'

    return 'word'  # default file type is word
