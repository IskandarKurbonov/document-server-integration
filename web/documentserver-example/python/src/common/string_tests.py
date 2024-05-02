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
from .string import boolean


class BooleanDefaultTests(TestCase):
    def test_converts_to_the_default_value(self):
        value = boolean("unknown")
        self.assertFalse(value)

    def test_converts_to_the_negative_value_by_default(self):
        value = boolean("unknown", False)
        self.assertFalse(value)

    def test_converts_to_the_positive_value_by_default(self):
        value = boolean("unknown", True)
        self.assertTrue(value)


class BooleanOptionalTests(TestCase):
    def test_converts_to_the_default_value(self):
        value = boolean(None)
        self.assertFalse(value)

    def test_converts_to_the_negative_value_by_default(self):
        value = boolean(None, False)
        self.assertFalse(value)

    def test_converts_to_the_positive_value_by_default(self):
        value = boolean(None, True)
        self.assertTrue(value)


class BooleanNegativeTests(TestCase):
    def test_converts_a_negative_string_to_the_negative_value(self):
        for string in ["false", "f", "no", "n", "0"]:
            value = boolean(string)
            self.assertFalse(value)


class BooleanPositiveTests(TestCase):
    def test_converts_a_positive_string_to_the_positive_value(self):
        for string in ["true", "t", "yes", "y", "1"]:
            value = boolean(string)
            self.assertTrue(value)
