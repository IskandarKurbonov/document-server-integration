#
# (c) Copyright Ascensio System SIA 2023
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# frozen_string_literal: true
# typed: true

require 'uri'

# The Configuration class provides the environment based configuration for the
# example instance.
# rubocop:disable Metrics/ClassLength
class Configuration
  extend T::Sig

  def initialize
    @version = T.let('1.5.1', String)
  end

  # The URI through which an example instance can be accessed.
  sig { returns(T.nilable(URI::Generic)) }
  def example_uri
    url = ENV['EXAMPLE_URL']
    return nil if url.nil?
    URI.parse(url)
  end

  # The public URI through which a document-server instance can be accessed.
  # For example, it can be used to grant the user's browser access to a
  # document-server.
  sig { returns(URI::Generic) }
  def document_server_public_uri
    url = ENV['DOCUMENT_SERVER_PUBLIC_URL'] || 'http://document-server/'
    URI.parse(url)
  end

  # The private URI through which a document-server instance can be accessed.
  # If no private URI is specified, it will be redirected to the public URI.
  # For example, it can be used when both an example instance and a
  # document-server instance are on the same closed network.
  sig { returns(URI::Generic) }
  def document_server_private_uri
    url = ENV['DOCUMENT_SERVER_PRIVATE_URL']
    return document_server_public_uri if url.nil?
    URI.parse(url)
  end

  sig { returns(URI::Generic) }
  def document_server_api_uri
    path = ENV['DOCUMENT_SERVER_API_PATH'] ||
           'web-apps/apps/api/documents/api.js'
    URI.join(document_server_public_uri.to_s, path)
  end

  sig { returns(URI::Generic) }
  def document_server_preloader_uri
    path = ENV['DOCUMENT_SERVER_PRELOADER_PATH'] ||
           'web-apps/apps/api/documents/cache-scripts.html'
    URI.join(document_server_public_uri.to_s, path)
  end

  sig { returns(URI::Generic) }
  def document_server_command_uri
    path = ENV['DOCUMENT_SERVER_COMMAND_PATH'] ||
           'coauthoring/CommandService.ashx'
    URI.join(document_server_private_uri.to_s, path)
  end

  sig { returns(URI::Generic) }
  def document_server_converter_uri
    path = ENV['DOCUMENT_SERVER_CONVERTER_PATH'] ||
           'ConvertService.ashx'
    URI.join(document_server_private_uri.to_s, path)
  end

  sig { returns(String) }
  def jwt_secret
    ENV['JWT_SECRET'] || ''
  end

  sig { returns(String) }
  def jwt_header
    ENV['JWT_HEADER'] || 'Authorization'
  end

  sig { returns(T::Boolean) }
  def jwt_use_for_request
    env = ENV['JWT_USE_FOR_REQUEST']
    return ActiveModel::Type::Boolean.new.cast(env) if env
    true
  end

  sig { returns(T::Boolean) }
  def ssl_verify_peer_mode_enabled
    env = ENV['SSL_VERIFY_PEER_MODE_ENABLED']
    return ActiveModel::Type::Boolean.new.cast(env) if env
    false
  end

  sig { returns(String) }
  def storage_path
    ENV['STORAGE_PATH'] || 'app_data'
  end

  sig { returns(Numeric) }
  def maximum_file_size
    env = ENV['MAXIMUM_FILE_SIZE']
    return env.to_i if env
    5 * 1024 * 1024
  end

  sig { returns(Numeric) }
  def convertation_timeout
    120
  end

  sig { returns(T::Array[String]) }
  def fillable_file_extensions
    '.docx|.oform'
      .split('|')
  end

  sig { returns(T::Array[String]) }
  def viewable_file_extensions
    '.djvu|.oxps|.pdf|.xps'
      .split('|')
  end

  sig { returns(T::Array[String]) }
  def editable_file_extensions
    '.csv|.docm|.docx|.docxf|.dotm|.dotx|.epub|.fb2|.html|.odp|.ods|.odt|.otp|.ots|.ott|.potm|.potx|.ppsm|.ppsx|.pptm|.pptx|.rtf|.txt|.xlsm|.xlsx|.xltm|.xltx'
      .split('|')
  end

  sig { returns(T::Array[String]) }
  def convertable_file_extensions
    '.doc|.dot|.dps|.dpt|.epub|.et|.ett|.fb2|.fodp|.fods|.fodt|.htm|.html|.mht|.mhtml|.odp|.ods|.odt|.otp|.ots|.ott|.pot|.pps|.ppt|.rtf|.stw|.sxc|.sxi|.sxw|.wps|.wpt|.xls|.xlsb|.xlt|.xml'
      .split('|')
  end

  sig { returns(T::Hash[String, String]) }
  def languages
    {
      'en' => 'English',
      'hy' => 'Armenian',
      'az' => 'Azerbaijani',
      'eu' => 'Basque',
      'be' => 'Belarusian',
      'bg' => 'Bulgarian',
      'ca' => 'Catalan',
      'zh' => 'Chinese (Simplified)',
      'zh-TW' => 'Chinese (Traditional)',
      'cs' => 'Czech',
      'da' => 'Danish',
      'nl' => 'Dutch',
      'fi' => 'Finnish',
      'fr' => 'French',
      'gl' => 'Galego',
      'de' => 'German',
      'el' => 'Greek',
      'hu' => 'Hungarian',
      'id' => 'Indonesian',
      'it' => 'Italian',
      'ja' => 'Japanese',
      'ko' => 'Korean',
      'lo' => 'Lao',
      'lv' => 'Latvian',
      'ms' => 'Malay (Malaysia)',
      'no' => 'Norwegian',
      'pl' => 'Polish',
      'pt' => 'Portuguese (Brazil)',
      'pt-PT' => 'Portuguese (Portugal)',
      'ro' => 'Romanian',
      'ru' => 'Russian',
      'si' => 'Sinhala (Sri Lanka)',
      'sk' => 'Slovak',
      'sl' => 'Slovenian',
      'es' => 'Spanish',
      'sv' => 'Swedish',
      'tr' => 'Turkish',
      'uk' => 'Ukrainian',
      'vi' => 'Vietnamese',
      'aa-AA' => 'Test Language'
    }
  end
end
# rubocop:enable Metrics/ClassLength
