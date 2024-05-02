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

from typing import Optional


def boolean(string: Optional[str], default: bool = False) -> bool:
    '''
    Converts a string that represents a boolean value to its corresponding
    boolean value. It supports case-insensitive `true`, `t`, `yes`, `y`, and `1`
    for the positive value, and `false`, `f`, `no`, `n`, and `0` for the
    negative value. If the string doesn't match any of these values, returns the
    default value.
    '''
    if string is None:
        return default

    lower = string.lower()

    positive = lower in ["true", "t", "yes", "y", "1"]
    if positive:
        return True

    negative = lower in ["false", "f", "no", "n", "0"]
    if negative:
        return False

    return default
