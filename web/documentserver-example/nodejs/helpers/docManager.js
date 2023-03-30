'use strict';
/**
 *
 * (c) Copyright Ascensio System SIA 2023
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

const path = require('path');
const fileSystem = require('fs');
const fileUtility = require('./fileUtility');
const documentService = require('./documentService');
const configServer = require('config').get('server');
const storageConfigFolder = configServer.get('storageFolder');

const DocManager = function (req, res) {
  this.req = req;
  this.res = res;
}

// check if the path exists or not
DocManager.prototype.existsSync = function (path) {
  let res = true;
  try {
    fileSystem.accessSync(path, fileSystem.F_OK); // synchronously test the user's permissions for the directory specified by path; the directory is visible to the calling process
  } catch (e) { // the response is set to false, if an error occurs
    res = false;
  }
  return res;
};

// create a new directory if it doesn't exist
DocManager.prototype.createDirectory = function (path) {
  if (!this.existsSync(path)) {
    fileSystem.mkdirSync(path);
  }
};

// get the language from the request
DocManager.prototype.getLang = function () {
  if (new RegExp('^[a-z]{2}(-[A-Z]{2})?$', 'i').test(this.req.query.lang)) {
    return this.req.query.lang;
  } // the default language value is English
  return 'en'
    
};

// get customization parameters
DocManager.prototype.getCustomParams = function () {
  let params = '';

  const {userid} = this.req.query; // user id
  params += (userid ? `&userid=${ userid}` : '');

  const {lang} = this.req.query; // language
  params += (lang ? `&lang=${ this.getLang()}` : '');

  const {directUrl} = this.req.query; // directUrl
  params += (directUrl ? `&directUrl=${ directUrl == 'true'}` : '');

  const {fileName} = this.req.query; // file name
  params += (fileName ? `&fileName=${ fileName}` : '');

  const {mode} = this.req.query; // mode: view/edit/review/comment/fillForms/embedded
  params += (mode ? `&mode=${ mode}` : '');

  const {type} = this.req.query; // type: embedded/mobile/desktop
  params += (type ? `&type=${ type}` : '');

  return params;
};

// get the correct file name if such a name already exists
DocManager.prototype.getCorrectName = function (fileName, userAddress) {
  const baseName = fileUtility.getFileName(fileName, true); // get file name from the url without extension
  const ext = fileUtility.getFileExtension(fileName); // get file extension from the url
  let name = baseName + ext; // get full file name
  let index = 1;

  while (this.existsSync(this.storagePath(name, userAddress))) { // if the file with such a name already exists in this directory
    name = `${baseName } (${ index })${ ext}`; // add an index after its base name
    index += 1;
  }

  return name;
};

// processes a request editnew
DocManager.prototype.requestEditnew = function (req, fileName, user) {
  let correctName = fileName;
  if (req.params.id != fileName) { // processes a repeated request editnew
    this.fileRemove(req.params.id);
    correctName = this.getCorrectName(req.params.id);
  }
  this.fileSizeZero(correctName);
  this.saveFileData(correctName, user.id, user.name);

  return correctName;
}

// delete a file with its history
DocManager.prototype.fileRemove = function (fileName) {
  const filePath = this.storagePath(fileName); // get the path to this file
  fileSystem.unlinkSync(filePath); // and delete it

  const userAddress = this.curUserHostAddress();
  const historyPath = this.historyPath(fileName, userAddress, true);
  this.cleanFolderRecursive(historyPath, true); // clean all the files from the history folder
}

// create a zero-size file
DocManager.prototype.fileSizeZero = function (fileName) {
  let path = this.storagePath(fileName);
  let fh = fileSystem.openSync(path, 'w');
  fileSystem.closeSync(fh);
}

// create demo document
DocManager.prototype.createDemo = function (isSample, fileExt, userid, username, wopi) {

  const demoName = `${isSample ? 'sample' : 'new' }.${ fileExt}`;
  const fileName = this.getCorrectName(demoName); // get the correct file name if such a name already exists

  this.copyFile(path.join(__dirname, '..','public', 'assets', isSample ? 'sample' : 'new', demoName), this.storagePath(fileName)); // copy sample document of a necessary extension to the storage path

  this.saveFileData(fileName, userid, username); // save file data to the file

  return fileName;
};

// save file data to the file
DocManager.prototype.saveFileData = function (fileName, userid, username, userAddress) {
  let address = userAddress;
  if (!address) {
    address = this.curUserHostAddress(); // get current user host address
  }
  // get full creation date of the document
  const date_create = fileSystem.statSync(this.storagePath(fileName, address)).mtime;
  const minutes = (date_create.getMinutes() < 10 ? '0' : '') + date_create.getMinutes().toString();
  const month = (date_create.getMonth() < 10 ? '0' : '') + (parseInt(date_create.getMonth().toString()) + 1);
  const sec = (date_create.getSeconds() < 10 ? '0' : '') + date_create.getSeconds().toString();
  const date_format = `${date_create.getFullYear() }-${ month }-${ date_create.getDate() } ${ date_create.getHours() }:${ minutes }:${ sec}`;

  const file_info = this.historyPath(fileName, address, true); // get file history information
  this.createDirectory(file_info); // create a new history directory if it doesn't exist

  fileSystem.writeFileSync(path.join(file_info, `${fileName }.txt`), `${date_format },${ userid },${ username}`); // write all the file information to a new txt file
};

// get file data
DocManager.prototype.getFileData = function (fileName, userAddress) {
  const history = path.join(this.historyPath(fileName, userAddress, true), `${fileName }.txt`); // get the path to the file with file information
  if (!this.existsSync(history)) { // if such a file doesn't exist
    return ['2017-01-01', 'uid-1', 'John Smith']; // return default information
  }

  return ((fileSystem.readFileSync(history)).toString())
    .split(',');
};

// get server url
DocManager.prototype.getServerUrl = function (forDocumentServer) {
  return (forDocumentServer && !!configServer.get('exampleUrl')) ? configServer.get('exampleUrl') : this.getServerPath();
};

// get server address from the request
DocManager.prototype.getServerPath = function () {
  return this.getServerHost() + (this.req.headers['x-forwarded-path'] || this.req.baseUrl);
};

// get host address from the request
DocManager.prototype.getServerHost = function () {
  return `${this.getProtocol() }://${ this.req.headers['x-forwarded-host'] || this.req.headers.host}`;
};

// get protocol from the request
DocManager.prototype.getProtocol = function () {
  return this.req.headers['x-forwarded-proto'] || this.req.protocol;
};

// get callback url
DocManager.prototype.getCallback = function (fileName) {
  const server = this.getServerUrl(true);
  const hostAddress = this.curUserHostAddress();
  const handler = `/track?filename=${ encodeURIComponent(fileName) }&useraddress=${ encodeURIComponent(hostAddress)}`; // get callback handler

  return server + handler;
};

// get url to the created file
DocManager.prototype.getCreateUrl = function (docType, userid, type, lang) {
  const server = this.getServerUrl();
  let ext = this.getInternalExtension(docType).replace('.', '');
  const handler = `/editor?fileExt=${ ext }&userid=${ userid }&type=${ type }&lang=${ lang}`;

  return server + handler;
}

// get url to download a file
DocManager.prototype.getDownloadUrl = function (fileName, forDocumentServer) {
  const server = this.getServerUrl(forDocumentServer);
  let handler = `/download?fileName=${ encodeURIComponent(fileName)}`;
  if (forDocumentServer) {
    const hostAddress = this.curUserHostAddress();
    handler += `&useraddress=${ encodeURIComponent(hostAddress)}`;
  }

  return server + handler;
};

DocManager.prototype.storageRootPath = function (userAddress) {
  return path.join(storageConfigFolder, this.curUserHostAddress(userAddress)); // get the path to the directory for the host address
}

// get the storage path of the given file
DocManager.prototype.storagePath = function (fileName, userAddress) {
  let fileNameExt = fileUtility.getFileName(fileName); // get the file name with extension
  const directory = this.storageRootPath(userAddress);
  this.createDirectory(directory); // create a new directory if it doesn't exist
  return path.join(directory, fileNameExt); // put the given file to this directory
};

// get the path to the forcesaved file version
DocManager.prototype.forcesavePath = function (fileName, userAddress, create) {
  let directory = this.storageRootPath(userAddress);
  if (!this.existsSync(directory)) { // the directory with host address doesn't exist
    return '';
  }
  directory = path.join(directory, `${fileName }-history`); // get the path to the history of the given file
  if (!create && !this.existsSync(directory)) { // the history directory doesn't exist and we are not supposed to create it
    return '';
  }
  this.createDirectory(directory); // create history directory if it doesn't exist
  directory = path.join(directory, fileName); // and get the path to the given file
  if (!create && !this.existsSync(directory)) {
    return '';
  }
  return directory;
};

// create the path to the file history
DocManager.prototype.historyPath = function (fileName, userAddress, create) {
  let directory = this.storageRootPath(userAddress);
  if (!this.existsSync(directory)) {
    return '';
  }
  directory = path.join(directory, `${fileName }-history`);
  if (!create && !this.existsSync(path.join(directory, '1'))) {
    return '';
  }
  return directory;
};

// get the path to the specified file version
DocManager.prototype.versionPath = function (fileName, userAddress, version) {
  const historyPath = this.historyPath(fileName, userAddress, true); // get the path to the history of a given file or create it if it doesn't exist
  return path.join(historyPath, `${ version}`);
};

// get the path to the previous file version
DocManager.prototype.prevFilePath = function (fileName, userAddress, version) {
  return path.join(this.versionPath(fileName, userAddress, version), `prev${ fileUtility.getFileExtension(fileName)}`);
};

// get the path to the file with document versions differences
DocManager.prototype.diffPath = function (fileName, userAddress, version) {
  return path.join(this.versionPath(fileName, userAddress, version), 'diff.zip');
};

// get the path to the file with document changes
DocManager.prototype.changesPath = function (fileName, userAddress, version) {
  return path.join(this.versionPath(fileName, userAddress, version), 'changes.txt');
};

// get the path to the file with key value in it
DocManager.prototype.keyPath = function (fileName, userAddress, version) {
  return path.join(this.versionPath(fileName, userAddress, version), 'key.txt');
};

// get the path to the file with the user information
DocManager.prototype.changesUser = function (fileName, userAddress, version) {
  return path.join(this.versionPath(fileName, userAddress, version), 'user.txt');
};

// get all the stored files
DocManager.prototype.getStoredFiles = function () {
  const userAddress = this.curUserHostAddress();
  const directory = this.storageRootPath(userAddress);
  this.createDirectory(directory);
  const result = [];
  const storedFiles = fileSystem.readdirSync(directory); // read the user host directory contents
  for (let i = 0; i < storedFiles.length; i += 1) { // run through all the elements from the folder
    const stats = fileSystem.lstatSync(path.join(directory, storedFiles[i])); // save element parameters

    if (!stats.isDirectory()) { // if the element isn't a directory
      const historyPath = this.historyPath(storedFiles[i], userAddress); // get the path to the file history
      let version = 0;
      if (historyPath != '') { // if the history path exists
        version = this.countVersion(historyPath); // get the last file version
      }

      const time = stats.mtime.getTime(); // get the time of element modification
      const item = { // create an object with element data
        time,
        name: storedFiles[i],
        documentType: fileUtility.getFileType(storedFiles[i]),
        canEdit: configServer.get('editedDocs').indexOf(fileUtility.getFileExtension(storedFiles[i])) != -1,
        version: version + 1
      };

      if (!result.length) { // if the result array is empty
        result.push(item); // push the item object to it
      } else {
        let j = 0;
        for (; j < result.length; j += 1) {
          if (time > result[j].time) { // otherwise, run through all the objects from the result array
            break;
          }
        }
        result.splice(j, 0, item); // and add new object in ascending order of time
      }
    }
  }
  return result;
};

// get current user host address
DocManager.prototype.curUserHostAddress = function (userAddress) {
  let address = userAddress;
  if (!address) { // if user address isn't passed to the function
    address = this.req.headers['x-forwarded-for'] || this.req.connection.remoteAddress; // take it from the header or use the remote address
  }

  return address.replace(new RegExp('[^0-9a-zA-Z.=]', 'g'), '_');
};

// copy file
DocManager.prototype.copyFile = function (exist, target) {
  fileSystem.writeFileSync(target, fileSystem.readFileSync(exist));
};

// get an internal extension
DocManager.prototype.getInternalExtension = function (fileType) {
  if (fileType == fileUtility.fileType.word) { // .docx for word type
    return '.docx';
  }

  if (fileType == fileUtility.fileType.cell) { // .xlsx for cell type
    return '.xlsx';
  }

  if (fileType == fileUtility.fileType.slide) { // .pptx for slide type
    return '.pptx';
  }

  return '.docx'; // the default value is .docx
};

// get the template image url
DocManager.prototype.getTemplateImageUrl = function (fileType) {
  const path = this.getServerUrl(true);
  if (fileType == fileUtility.fileType.word) { // for word type
    return `${path}/images/file_docx.svg`;
  }

  if (fileType == fileUtility.fileType.cell) { // for cell type
    return `${path}/images/file_xlsx.svg`;
  }

  if (fileType == fileUtility.fileType.slide) { // for slide type
    return `${path}/images/file_pptx.svg`;
  }

  return `${path }/images/file_docx.svg`; // the default value
}

// get document key
DocManager.prototype.getKey = function (fileName, userAddress) {
  let address = userAddress || this.curUserHostAddress();
  let key = address + fileName; // get document key by adding local file url to the current user host address

  const historyPath = this.historyPath(fileName, address); // get the path to the file history
  if (historyPath != '') { // if the path to the file history exists
    key += this.countVersion(historyPath); // add file version number to the document key
  }

  const storagePath = this.storagePath(fileName, address); // get the storage path to the given file
  const stat = fileSystem.statSync(storagePath); // get file information
  key += stat.mtime.getTime(); // and add creation time to the document key

  return documentService.generateRevisionId(key); // generate the document key value
};

// get current date
DocManager.prototype.getDate = function (date) {
  const minutes = (date.getMinutes() < 10 ? '0' : '') + date.getMinutes().toString();
  return `${date.getMonth() }/${ date.getDate() }/${ date.getFullYear() } ${ date.getHours() }:${ minutes}`;
};

// get changes made in the file
DocManager.prototype.getChanges = function (fileName) {
  if (this.existsSync(fileName)) { // if the directory with such a file exists
    return JSON.parse(fileSystem.readFileSync(fileName)); // read this file and parse it
  }
  return null;
};

// get the last file version
DocManager.prototype.countVersion = function (directory) {
  let i = 0;
  while (this.existsSync(path.join(directory, `${ i + 1}`))) { // run through all the file versions
    i += 1; // and count them
  }
  return i;
};

// get file history information
DocManager.prototype.getHistory = function (fileName, content, keyVersion, version) {
  let oldVersion = false;
  let contentJson = null;
  let fileContent = content;
  let userNameFromJson = null;
  let userIdFromJson = null;
  let createdFromJson = null;
  if (fileContent) { // if content is defined
    if (fileContent.changes && fileContent.changes.length) { // and there are some modifications in the content
      [contentJson] = fileContent.changes; // write these modifications to the json content
    } else if (fileContent.length) {
      [contentJson] = fileContent; // otherwise, write original content to the json content
      oldVersion = true; // and note that this is an old version
    } else {
      fileContent = false;
    }
  }

  const userAddress = this.curUserHostAddress();

  if (content && contentJson) {
    userNameFromJson = oldVersion ? contentJson.username : contentJson.user.name;
    userIdFromJson = oldVersion ? contentJson.userid : contentJson.user.userid;
    createdFromJson = oldVersion ? contentJson.date : contentJson.created;
  }

  const username = userNameFromJson ? userNameFromJson : (this.getFileData(fileName, userAddress))[2];
  const userid = userIdFromJson ? userIdFromJson : (this.getFileData(fileName, userAddress))[1];
  const created = createdFromJson ? createdFromJson : (this.getFileData(fileName, userAddress))[0];
  const res = (fileContent && !oldVersion) ? fileContent : {changes: fileContent};
  res.key = keyVersion; // write the information about the user, creation time, key and version to the result object
  res.version = version;
  res.created = created;
  res.user = {
    id: userid,
    name: username != 'null' ? username : null
  };

  return res;
};

// clean folder
DocManager.prototype.cleanFolderRecursive = function (folder, me) {
  if (fileSystem.existsSync(folder)) { // if the given folder exists
    const files = fileSystem.readdirSync(folder);
    files.forEach((file) => { // for each file from the folder
      const curPath = path.join(folder, file); // get its current path
      if (fileSystem.lstatSync(curPath).isDirectory()) {
        this.cleanFolderRecursive(curPath, true); // for each folder included in this one repeat the same procedure
      } else {
        fileSystem.unlinkSync(curPath); // remove the file
      }
    });
    if (me) {
      fileSystem.rmdirSync(folder);
    }
  }
};

// get files information
DocManager.prototype.getFilesInfo = function (fileId) {
  const userAddress = this.curUserHostAddress();
  const directory = this.storageRootPath(userAddress);
  const filesInDirectory = this.getStoredFiles(); // get all the stored files from the folder
  const responseArray = [];
  let responseObject;
  for (let currentFile = 0; currentFile < filesInDirectory.length; currentFile += 1) { // run through all the files from the directory
    const file = filesInDirectory[currentFile];
    const stats = fileSystem.lstatSync(path.join(directory, file.name)); // get file information
    const fileObject = { // write file parameters to the file object
      version: file.version,
      id: this.getKey(file.name),
      contentLength: `${(stats.size / 1024).toFixed(2)} KB`,
      pureContentLength: stats.size,
      title: file.name,
      updated: stats.mtime
    };
    if (fileId !== undefined) { // if file id is defined
      if (this.getKey(file.name) == fileId) { // and it is equal to the document key value
        responseObject = fileObject; // response object will be equal to the file object
        break;
      }
    } else responseArray.push(fileObject); // otherwise, push file object to the response array
  };
  if (fileId !== undefined) {
    if (responseObject !== undefined) return responseObject;
    return 'File not found';
  } return responseArray;
};

DocManager.prototype.getInstanceId = function () {
  return this.getServerUrl();
};

// save all the functions to the DocManager module to export it later in other files
module.exports = DocManager;
