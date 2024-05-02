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

require 'jwt'
require_relative '../configuration/configuration'

# Helper class for JSON Web Token (JWT) operations, including encoding and decoding.
class JwtHelper
  @jwt_secret = ConfigurationManager.new.jwt_secret
  @token_use_for_request = ConfigurationManager.new.jwt_use_for_request

  # check if a secret key to generate token exists or not
  def self.enabled?
    @jwt_secret.present?
  end

  # check if a secret key used for request
  def self.use_for_request
    @token_use_for_request
  end

  # encode a payload object into a token using a secret key
  def self.encode(payload)
    JWT.encode(payload, @jwt_secret, 'HS256') # define the hashing algorithm and get token
  end

  # decode a token into a payload object using a secret key
  def self.decode(token)
    begin
      decoded = JWT.decode(token, @jwt_secret, true, { algorithm: 'HS256' })
    rescue StandardError
      return ''
    end
    # decoded = Array [ {"data"=>"test"}, # payload
    #                   {"alg"=>"HS256"} # header   ]
    decoded[0].to_json # get json payload
  end
end
