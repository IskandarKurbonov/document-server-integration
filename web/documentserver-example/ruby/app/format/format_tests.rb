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

# frozen_string_literal: true
# typed: true

require 'json'
require 'test/unit'
require_relative 'format'

# Test case for the Format class.
class FormatTests < Test::Unit::TestCase
  def test_generates_extension
    content =
      '
      {
        "name": "djvu",
        "type": "word",
        "actions": ["view"],
        "convert": ["bmp", "gif", "jpg", "pdf", "pdfa", "png"],
        "mime": ["image/vnd.djvu"]
      }
      '
    hash = JSON.parse(content)
    format = Format.from_hash(hash)
    assert_equal('.djvu', format.extension)
  end
end

# Test case for the FormatManager class, checks availability "all" formats.
class FormatManagerAllTests < Test::Unit::TestCase
  def test_loads
    format_manager = FormatManager.new
    assert_false(format_manager.all.empty?)
  end
end

# Test case for the FormatManager class, checks availability "documents" formats.
class FormatManagerDocumentsTests < Test::Unit::TestCase
  def test_loads
    format_manager = FormatManager.new
    assert_false(format_manager.documents.empty?)
  end
end

# Test case for the FormatManager class, checks availability "presentations" formats.
class FormatManagerPresentationsTests < Test::Unit::TestCase
  def test_loads
    format_manager = FormatManager.new
    assert_false(format_manager.presentations.empty?)
  end
end

# Test case for the FormatManager class, checks availability "spreadsheets" formats.
class FormatManagerSpreadsheetsTests < Test::Unit::TestCase
  def test_loads
    format_manager = FormatManager.new
    assert_false(format_manager.spreadsheets.empty?)
  end
end

# Test case for the FormatManager class, checks availability "all convertible" formats.
class FormatManagerConvertibleTests < Test::Unit::TestCase
  def test_loads
    format_manager = FormatManager.new
    assert_false(format_manager.all.empty?)
  end
end

# Test case for the FormatManager class, checks availability "all editable" formats.
class FormatManagerEditableTests < Test::Unit::TestCase
  def test_loads
    format_manager = FormatManager.new
    assert_false(format_manager.all.empty?)
  end
end

# Test case for the FormatManager class, checks availability "all viewable" formats.
class FormatManagerViewableTests < Test::Unit::TestCase
  def test_loads
    format_manager = FormatManager.new
    assert_false(format_manager.all.empty?)
  end
end

# Test case for the FormatManager class, checks availability "all filable" formats.
class FormatManagerFilableTests < Test::Unit::TestCase
  def test_loads
    format_manager = FormatManager.new
    assert_false(format_manager.all.empty?)
  end
end
