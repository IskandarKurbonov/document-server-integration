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

from __future__ import annotations
from unittest import TestCase
from msgspec.json import decode
from . import Format, FormatManager


class FormatTests(TestCase):
    json = \
        '''
        {
          "name": "djvu",
          "type": "word",
          "actions": ["view"],
          "convert": ["bmp", "gif", "jpg", "pdf", "pdfa", "png"],
          "mime": ["image/vnd.djvu"]
        }
        '''

    def test_generates_extension(self):
        form = decode(self.json, type=Format)
        self.assertEqual(form.extension(), '.djvu')


class FormatManagerAllTests(TestCase):
    def test_loads(self):
        format_manager = FormatManager()
        formats = format_manager.all()
        empty = len(formats) == 0
        self.assertFalse(empty)


class FormatManagerDocumentsTests(TestCase):
    def test_loads(self):
        format_manager = FormatManager()
        formats = format_manager.documents()
        empty = len(formats) == 0
        self.assertFalse(empty)


class FormatManagerPresentationsTests(TestCase):
    def test_loads(self):
        format_manager = FormatManager()
        formats = format_manager.presentations()
        empty = len(formats) == 0
        self.assertFalse(empty)


class FormatManagerSpreadsheetsTests(TestCase):
    def test_loads(self):
        format_manager = FormatManager()
        formats = format_manager.spreadsheets()
        empty = len(formats) == 0
        self.assertFalse(empty)


class FormatManagerConvertibleTests(TestCase):
    def test_loads(self):
        format_manager = FormatManager()
        formats = format_manager.convertible()
        empty = len(formats) == 0
        self.assertFalse(empty)


class FormatManagerEditableTests(TestCase):
    def test_loads(self):
        format_manager = FormatManager()
        formats = format_manager.editable()
        empty = len(formats) == 0
        self.assertFalse(empty)


class FormatManagerViewableTests(TestCase):
    def test_loads(self):
        format_manager = FormatManager()
        formats = format_manager.viewable()
        empty = len(formats) == 0
        self.assertFalse(empty)


class FormatManagerFillableTests(TestCase):
    def test_loads(self):
        format_manager = FormatManager()
        formats = format_manager.fillable()
        empty = len(formats) == 0
        self.assertFalse(empty)
