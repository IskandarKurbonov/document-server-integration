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

import jwt
from src.configuration import ConfigurationManager

config_manager = ConfigurationManager()


# check if a secret key to generate token exists or not
def isEnabled():
    return bool(config_manager.jwt_secret())


# check if a secret key to generate token exists or not
def useForRequest():
    return config_manager.jwt_use_for_request()


# encode a payload object into a token using a secret key and decodes it into the utf-8 format
def encode(payload):
    return jwt.encode(payload, config_manager.jwt_secret(), algorithm='HS256')


# decode a token into a payload object using a secret key
def decode(string):
    return jwt.decode(string, config_manager.jwt_secret(), algorithms=['HS256'])
