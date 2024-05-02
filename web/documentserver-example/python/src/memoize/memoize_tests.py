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
from . import memoize


class MemoizeMock():
    counter: int = 1

    @memoize
    def method(self) -> int:
        return self.counter


class MemoizeTests(TestCase):
    def test(self):
        mock = MemoizeMock()
        self.assertEqual(mock.counter, 1)
        self.assertEqual(mock.method(), 1)
        mock.counter += 1
        self.assertEqual(mock.counter, 2)
        self.assertEqual(mock.method(), 1)
