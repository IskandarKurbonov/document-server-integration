// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

const lockDict = {};

// get the lock object of the specified file
const getLockObject = function getLockObject(filePath) {
  return lockDict[filePath];
};

// clear the lock timeout
const clearLockTimeout = function clearLockTimeout(lockObject) {
  if (lockObject && lockObject.timeout) {
    clearTimeout(lockObject.timeout);
  }
};

// get the lock value of the specified file
const getLockValue = function getLockValue(filePath) {
  const lock = getLockObject(filePath); // get the lock object of the specified file
  if (lock) return lock.value; // if it exists, get the lock value from it
  return '';
};

// check if the specified file path has lock or not
const hasLock = function hasLock(filePath) {
  return !!getLockObject(filePath);
};

// allow for file editing
const unlock = function unlock(filePath) {
  const lock = getLockObject(filePath); // get the lock of the specified file
  clearLockTimeout(lock); // clear its timeout
  delete lockDict[filePath]; // delete the lock
};

// lock file editing
const lock = function lock(filePath, lockValue) {
  const oldLock = getLockObject(filePath); // get the old lock of the specified file
  clearLockTimeout(oldLock); // clear its timeout

  // create a new lock object
  lockDict[filePath] = {
    value: lockValue,
    timeout: setTimeout(unlock, 1000 * 60 * 30, filePath), // set lock for 30 minutes
  };
};

module.exports = {
  hasLock,
  getLock: getLockValue,
  lock,
  unlock,
};
