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

require 'sorbet-runtime'
require 'uri'
require_relative '../configuration/configuration'

# Class manages URI resolution, redirecting public URLs to private ones based on the configuration.
class ProxyManager
  extend T::Sig

  sig { params(config_manager: ConfigurationManager).void }
  def initialize(config_manager:)
    @config_manager = config_manager
  end

  sig { params(uri: URI::Generic).returns(URI::Generic) }
  def resolve_uri(uri)
    return uri unless refer_public_url(uri)

    redirect_public_url(uri)
  end

  sig { params(uri: URI::Generic).returns(T::Boolean) }
  private def refer_public_url(uri)
    public_uri = @config_manager.document_server_public_uri
    uri.scheme == public_uri.scheme &&
    uri.host == public_uri.host &&
    uri.port == public_uri.port
  end

  sig { params(uri: URI::Generic).returns(URI::Generic) }
  private def redirect_public_url(uri)
    private_uri = @config_manager.document_server_private_uri
    redirected_uri = uri
    redirected_uri.scheme = private_uri.scheme
    redirected_uri.host = private_uri.host
    redirected_uri.port = private_uri.port
    redirected_uri
  end
end
