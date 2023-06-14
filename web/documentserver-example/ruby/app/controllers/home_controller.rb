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

# typed: true
# rubocop:disable Metrics/ClassLength
# rubocop:disable Metrics/MethodLength
# rubocop:disable Metrics/AbcSize
# rubocop:disable Metrics/CyclomaticComplexity
# rubocop:disable Metrics/PerceivedComplexity

require 'net/http'
require 'mimemagic'

class HomeController < ApplicationController
  def index
  end

  def editor

    DocumentHelper.init(request.remote_ip, request.base_url)
    user = Users.get_user(params[:userId])
    @file = FileModel.new(:file_name => File.basename(params[:fileName]), :mode => params[:editorsMode], :type => params[:editorsType], :user_ip => request.remote_ip, :lang => cookies[:ulang], :user => user, :action_data => params[:actionLink], :direct_url => params[:directUrl])

  end

  # creating a sample document
  def sample

    DocumentHelper.init(request.remote_ip, request.base_url)
    user = Users.get_user(params[:userId])
    file_name = DocumentHelper.create_demo(params[:fileExt], params[:sample], user)
    redirect_to :controller => 'home', :action => 'editor', :fileName => file_name, :userId => user.id

  end

  # uploading a file
  def upload

    DocumentHelper.init(request.remote_ip, request.base_url)

    begin
      http_posted_file = params[:file]
      file_name = http_posted_file.original_filename
      cur_size = http_posted_file.size

      # check if the file size exceeds the maximum file size
      if DocumentHelper.file_size_max < cur_size || cur_size <= 0
        raise 'File size is incorrect'
      end

      cur_ext = File.extname(file_name).downcase

      # check if the file extension is supported by the editor
      unless DocumentHelper.file_exts.include? cur_ext
        raise 'File type is not supported'
      end

      # get the correct file name if such a name already exists
      file_name = DocumentHelper.get_correct_name(file_name, nil)
      document_type = FileUtility.get_file_type(file_name)

      # write the uploaded file to the storage directory
      File.open(DocumentHelper.storage_path(file_name, nil), 'wb') do |file|
        file.write(http_posted_file.read)
      end

      # create file meta information
      user = Users.get_user(params[:userId])

      DocumentHelper.create_meta(file_name, user.id, user.name, nil)

      render plain: '{ "filename": "' + file_name + '", "documentType": "' + document_type + '"}'  # write a new file name to the response
    rescue => ex
      render plain: '{ "error": "' + ex.message + '"}'  # write an error message to the response
    end

  end

  # converting a file
  def convert

    begin
      file_data = request.body.read
      if file_data == nil || file_data.empty?
          return ""
      end

      body = JSON.parse(file_data)

      file_name = File.basename(body["filename"])
      lang = cookies[:ulang] ? cookies[:ulang] : "en"
      file_pass = body["filePass"] ? body["filePass"] : nil
      file_uri = DocumentHelper.get_download_url(file_name)
      extension = File.extname(file_name).downcase
      internal_extension = 'ooxml'

      if DocumentHelper.convert_exts.include? (extension)  # check if the file with such an extension can be converted
        key = ServiceConverter.generate_revision_id(file_uri)  # generate document key
        percent, new_file_uri, new_file_type  = ServiceConverter.get_converted_data(file_uri, extension.delete('.'), internal_extension.delete('.'), key, true, file_pass, lang)  # get the url and file type of the converted file and the conversion percentage

        # if the conversion isn't completed, write file name and step values to the response
        if percent != 100
          render plain: '{ "step" : "' + percent.to_s + '", "filename" : "' + file_name + '"}'
          return
        end

        # get the correct file name if such a name already exists
        correct_name = DocumentHelper.get_correct_name(File.basename(file_name, extension) + "." + new_file_type, nil)

        uri = URI.parse(new_file_uri)  # create the request url
        http = Net::HTTP.new(uri.host, uri.port)  # create a connection to the http server

        DocumentHelper.verify_ssl(new_file_uri, http)

        req = Net::HTTP::Get.new(uri.request_uri)  # create the get requets
        res = http.request(req)
        data = res.body

        if data == nil
          raise 'stream is null'
        end

        # write a file with a new extension, but with the content from the origin file
        File.open(DocumentHelper.storage_path(correct_name, nil), 'wb') do |file|
          file.write(data)
        end

        file_name = correct_name
        user = Users.get_user(params[:userId])

        DocumentHelper.create_meta(file_name, user.id, user.name, nil)  # create meta data of the new file
      end

      render plain: '{ "filename" : "' + file_name + '"}'
    rescue => ex
      render plain: '{ "error": "' + ex.message + '"}'
    end

  end

  # downloading a history file from public
  def downloadhistory
    begin
      file_name = File.basename(params[:fileName])
      user_address = params[:userAddress]
      version = params[:ver]
      file = params[:file]
      isEmbedded = params[:dmode]

      if JwtHelper.is_enabled && JwtHelper.use_for_request
        jwtHeader = Rails.configuration.header.empty? ? "Authorization" : Rails.configuration.header;
        if request.headers[jwtHeader]
          hdr = request.headers[jwtHeader]
          hdr.slice!(0, "Bearer ".length)
          token = JwtHelper.decode(hdr)
          if !token || token.eql?("")
            render plain: "JWT validation failed", :status => 403
            return
          end
        else
          render plain: "JWT validation failed", :status => 403
          return
        end
      end
      hist_path = DocumentHelper.storage_path(file_name, user_address) + "-hist" # or to the original document

      file_path = File.join(hist_path, version, file)

      # add headers to the response to specify the page parameters
      response.headers['Content-Length'] = File.size(file_path).to_s
      response.headers['Content-Type'] = MimeMagic.by_path(file_path).eql?(nil) ? nil : MimeMagic.by_path(file_path).type
      response.headers['Content-Disposition'] = "attachment;filename*=UTF-8\'\'" + ERB::Util.url_encode(file)

      send_file file_path, :x_sendfile => true
    rescue => ex
      render plain: '{ "error": "File not found"}'
    end
  end

  # tracking file changes
  def track
    file_data = TrackHelper.read_body(request)  # read the request body
    if file_data == nil || file_data.empty?
      render plain: '{"error":1}'  # an error occurs if the file is empty
      return
    end

    status = file_data['status'].to_i

    user_address = params[:userAddress]
    file_name = File.basename(params[:fileName])

    if status == 1  # editing
      if file_data['actions'][0]['type'] == 0  # finished edit
        user = file_data['actions'][0]['userid']  # get the user id
         if !file_data['users'].index(user)
          json_data = TrackHelper.command_request("forcesave", file_data['key'])  # call the forcesave command
         end
      end
    end

    if status == 2 || status == 3  # MustSave, Corrupted
      saved = TrackHelper.process_save(file_data, file_name, user_address)  # save file
      render plain: '{"error":' + saved.to_s + '}'
      return
    end

    if status == 6 || status == 7  # MustForceave, CorruptedForcesave
      saved = TrackHelper.process_force_save(file_data, file_name, user_address)  # force save file
      render plain: '{"error":' + saved.to_s + '}'
      return
    end

    render plain: '{"error":0}'
    return
  end

  # removing a file
  def remove
    file_name = File.basename(params[:filename])  # get the file name
    if !file_name  # if it doesn't exist
      render plain: '{"success":false}'  # report that the operation is unsuccessful
      return
    end

    DocumentHelper.init(request.remote_ip, request.base_url)
    storage_path = DocumentHelper.storage_path(file_name, nil)
    hist_dir = DocumentHelper.history_dir(storage_path)

    if File.exist?(storage_path)  # if the file exists
      File.delete(storage_path)  # delete it from the storage path
    end

    if Dir.exist?(hist_dir)  # if the history directory of this file exists
      FileUtils.remove_entry_secure(hist_dir)  # delete it
    end

    render plain: '{"success":true}'  # report that the operation is successful
    return
  end

  # getting files information
  def files
    file_id = params[:fileId]
    filesInfo = DocumentHelper.get_files_info(file_id)  # get the information about the file specified by a file id
    render json: filesInfo
  end

  # downloading a csv file
  def csv
    file_name = "csv.csv"
    csvPath = Rails.root.join('public', 'assets', 'sample', file_name)

    # add headers to the response to specify the page parameters
    response.headers['Content-Length'] = File.size(csvPath).to_s
    response.headers['Content-Type'] = MimeMagic.by_path(csvPath).type
    response.headers['Content-Disposition'] = "attachment;filename*=UTF-8\'\'" + ERB::Util.url_encode(file_name)

    send_file csvPath, :x_sendfile => true
  end

  # downloading a file
  def download
    begin
      file_name = File.basename(params[:fileName])
      user_address = params[:userAddress]
      isEmbedded = params[:dmode]

      if JwtHelper.is_enabled && isEmbedded == nil && user_address != nil && JwtHelper.use_for_request
        jwtHeader = Rails.configuration.header.empty? ? "Authorization" : Rails.configuration.header;
        if request.headers[jwtHeader]
            hdr = request.headers[jwtHeader]
            hdr.slice!(0, "Bearer ".length)
            token = JwtHelper.decode(hdr)
        end
        if !token || token.eql?("")
          render plain: "JWT validation failed", :status => 403
          return
        end
      end

      file_path = DocumentHelper.forcesave_path(file_name, user_address, false)  # get the path to the force saved document version
      if file_path.eql?("")
        file_path = DocumentHelper.storage_path(file_name, user_address)  # or to the original document
      end

      # add headers to the response to specify the page parameters
      response.headers['Content-Length'] = File.size(file_path).to_s
      response.headers['Content-Type'] = MimeMagic.by_path(file_path).eql?(nil) ? nil : MimeMagic.by_path(file_path).type
      response.headers['Content-Disposition'] = "attachment;filename*=UTF-8\'\'" + ERB::Util.url_encode(file_name)

      send_file file_path, :x_sendfile => true
    rescue => ex
      render plain: '{ "error": "File not found"}'
    end
  end

  # Save Copy as...
  def saveas
    begin
      body = JSON.parse(request.body.read)
      file_url = body["url"]
      title = body["title"]
      file_name = DocumentHelper.get_correct_name(title, nil)
      extension = File.extname(file_name).downcase
      all_exts = DocumentHelper.convert_exts + DocumentHelper.edited_exts + DocumentHelper.viewed_exts + DocumentHelper.fill_forms_exts

      unless all_exts.include?(extension)
        render plain: '{"error": "File type is not supported"}'
        return
      end

      uri = URI.parse(file_url)  # create the request url
      http = Net::HTTP.new(uri.host, uri.port)  # create a connection to the http server

      DocumentHelper.verify_ssl(file_url, http)

      req = Net::HTTP::Get.new(uri.request_uri)  # create the get requets
      res = http.request(req)
      data = res.body

      if data.size <= 0 || data.size > Configuration.new.maximum_file_size
        render plain: '{"error": "File size is incorrect"}'
        return
      end

      File.open(DocumentHelper.storage_path(file_name, nil), 'wb') do |file|
        file.write(data)
      end
      user = Users.get_user(params[:userId])
      DocumentHelper.create_meta(file_name, user.id, user.name, nil)  # create meta data of the new file

      render plain: '{"file" : "' + file_name + '"}'
      return
    rescue => ex
      render plain: '{"error":1, "message": "' + ex.message + '"}'
      return
    end
  end

    # Rename...
    def rename
      body = JSON.parse(request.body.read)
      dockey = body["dockey"]
      newfilename = body["newfilename"]

      orig_ext = '.' + body["ext"]
      cur_ext = File.extname(newfilename).downcase
      if orig_ext != cur_ext
        newfilename += orig_ext
      end

      meta = {
        :title => newfilename
      }

      json_data = TrackHelper.command_request("meta", dockey, meta)
      render plain: '{ "result" : "' + JSON.dump(json_data) + '"}'
    end

    #ReferenceData
    def reference
      body = JSON.parse(request.body.read)
      fileName = ""


      if body.key?("referenceData")
        referenceData = body["referenceData"]
        instanceId = referenceData["instanceId"]
        if instanceId == DocumentHelper.get_server_url(false)
          fileKey = JSON.parse(referenceData["fileKey"])
          userAddress = fileKey["userAddress"]
          if userAddress == DocumentHelper.cur_user_host_address(nil)
            fileName = fileKey["fileName"]
          end
        end
      end

      if fileName.empty? and body.key?("path")
        path = File.basename(body["path"])
        if File.exist?(DocumentHelper.storage_path(path, nil))
          fileName = path
        end
      end

      if fileName.empty?
        render plain: '{ "error": "File not found"}'
        return
      end

      data = {
        :fileType => DocumentHelper.get_internal_extension(fileName),
        :url => DocumentHelper.get_download_url(fileName),
        :directUrl => body["directUrl"] ? DocumentHelper.get_download_url(fileName) : DocumentHelper.get_download_url(fileName,false),
        :referenceData => {
          :instanceId => DocumentHelper.get_server_url(false),
          :fileKey => {:fileName => fileName,:userAddress => DocumentHelper.cur_user_host_address(nil)}.to_json
        },
        :path => fileName
      }

      if JwtHelper.is_enabled
        data["token"] = JwtHelper.encode(data)
      end

      render plain: data.to_json
    end

  # Bumps the source file's current version and restores it to the specified
  # version, thus designating it as the new source file.
  #
  # ```http
  # PUT /restore HTTP/1.1
  # Content-Type: application/json
  #
  # {
  #   "fileName": "the source file name with extension"
  #   "version": "the file version that needs to be restored"
  # }
  # ```
  def restore
    body = JSON.parse(request.body.read)

    source_basename = body['fileName']
    target_version = body['version']
    unless source_basename && target_version
      response.status = :bad_request
      render json: {
        error: 'The fileName or version parameters were not specified.',
        success: false
      }
      return
    end

    DocumentHelper.init(request.remote_ip, request.base_url)
    user_address = DocumentHelper.cur_user_host_address(nil)
    source_file = DocumentHelper.storage_path(source_basename, user_address)
    unless File.exist?(source_file)
      response.status = :not_found
      render json: {
        error: "The file with the specified fileName doesn't exist.",
        success: false
      }
      return
    end

    previous_name = 'prev'
    previous_extension = File.extname(source_basename)
    previous_basename = "#{previous_name}#{previous_extension}"
    history_directory = DocumentHelper.history_dir(source_file)

    target_directory = File.join(history_directory, target_version.to_s)
    target_file = File.join(target_directory, previous_basename)
    unless File.exist?(target_file)
      response.status = :not_found
      render json: {
        error: "The file with the specified version doesn't exist.",
        success: false
      }
      return
    end

    latest_version = DocumentHelper.get_file_version(history_directory)
    bumped_version = latest_version + 1
    bumped_directory = File.join(history_directory, bumped_version.to_s)
    bumped_file = File.join(bumped_directory, previous_basename)
    FileUtils.mkdir(bumped_directory) unless File.exist?(bumped_directory)

    bumped_key = ServiceConverter.generate_revision_id(
      "#{File.join(user_address, source_basename)}.#{File.mtime(source_file)}"
    )
    bumped_key_file = File.join(bumped_directory, 'key.txt')

    FileUtils.cp(source_file, bumped_file)
    FileUtils.cp(target_file, source_file)
    File.write(bumped_key_file, bumped_key)

    response_data = {
      error: nil,
      success: true,
      # TODO:
      user_address:,
      target_version:,
      file: 'changes.json',
      bumped_version:
    }

    if JwtHelper.is_enabled
      jwt_header = Rails.configuration.header.empty? ? 'Authorization' : Rails.configuration.header
      jwt_token = JwtHelper.encode(response_data)
      response.headers[jwt_header] = "Bearer #{jwt_token}"
    end

    response.status = :ok
    render json: response_data
  end

  # ```http
  # GET /history/{{file_basename}}?user_host={{user_host}} HTTP/1.1
  # ??Authorization: Bearer {{token}}
  # ```
  def history
    source_basename = T.let(params[:file_basename], String)

    DocumentHelper.init(request.remote_ip, request.base_url)

    user_host = DocumentHelper.cur_user_host_address(params[:user_host])
    source_file = DocumentHelper.storage_path(source_basename, user_host)
    unless File.exist?(source_file)
      response.status = :not_found
      render json: {
        error: "The file with the specified fileName doesn't exist.",
        success: false
      }
      return
    end

    source_extension = File.extname(source_file)
    source_key = ServiceConverter.generate_revision_id(
      "#{File.join(user_host, source_basename)}.#{File.mtime(source_file)}"
    )

    puts
  end

  # ```http
  # GET /history/{{file_basename}}/{{version}}?user_host={{user_host}} HTTP/1.1
  # Authorization: Bearer {{token}}
  # ```
  def history_of_version
    if JwtHelper.is_enabled && JwtHelper.use_for_request
      header_name = Rails.configuration.header.empty? ? 'Authorization' : Rails.configuration.header
      header = request.headers[header_name]
      unless header
        response.status = :forbidden
        render json: {
          error: 'forbidden',
          success: false
        }
        return
      end

      token = header.sub('Bearer ', '')
      decoded = JwtHelper.decode(token)
      unless decoded && !decoded.eql?('')
        response.status = :forbidden
        render json: {
          error: 'forbidden',
          success: false
        }
        return
      end
    end

    source_basename = T.let(params[:file_basename], String)
    target_version = T.let(params[:version], String).to_i
    unless target_version != 0
      response.status = :bad_request
      # if params[:version] == '0', version can't be null
      return
    end

    DocumentHelper.init(request.remote_ip, request.base_url)

    user_host = DocumentHelper.cur_user_host_address(params[:user_host])
    source_file = DocumentHelper.storage_path(source_basename, user_host)
    unless File.exist?(source_file)
      response.status = :not_found
      render json: {
        error: "The file with the specified fileName doesn't exist.",
        success: false
      }
      return
    end

    history_directory = DocumentHelper.history_dir(source_file)
    target_directory = File.join(history_directory, target_version.to_s)
    target_history_file = File.join(target_directory, 'changes.json')
    unless File.exist?(target_history_file)
      response.status = :not_found
      render json: {
        error: "The file with the specified version doesn't exist.",
        success: false
      }
      return
    end

    target_history_mimetype = MimeMagic.by_path(target_history_file)

    response.headers['Content-Length'] = File.size(target_history_file).to_s
    response.headers['Content-Type'] = target_history_mimetype.eql?(nil) ? nil : target_history_mimetype.type
    response.headers['Content-Disposition'] = "attachment;filename*=UTF-8''#{ERB::Util.url_encode('changes.json')}"
    response.status = :ok
    send_file target_history_file
  end
end
