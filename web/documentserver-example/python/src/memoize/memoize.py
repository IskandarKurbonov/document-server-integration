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

def memoize(func):
    def wrapper(*args, **kwargs):
        if wrapper.called:
            return wrapper.result
        wrapper.called = True
        wrapper.result = func(*args, **kwargs)
        return wrapper.result
    wrapper.called = False
    wrapper.result = None
    return wrapper
