# frozen_string_literal: true

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

require_relative '../format/format'

# Determination file type based on extensions, utilizing `@format_manager` for format management.
class FileUtility
  @format_manager = FormatManager.new

  class << self
    attr_reader :format_manager
  end

  def self.get_file_type(file_name)
    ext = File.extname(file_name).downcase

    return 'word' if FileUtility.format_manager.document_extensinons.include?(ext)
    return 'cell' if FileUtility.format_manager.spreadsheet_extensinons.include?(ext)
    return 'slide' if FileUtility.format_manager.presentation_extensinons.include?(ext)

    'word'
  end
end
