// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

let cache = {};

// write the key value and its creation time to the cache
exports.put = function put(key, value) {
  cache[key] = { value, time: new Date().getTime() };
};

// check if the given key is in the cache
exports.containsKey = function containsKey(key) {
  if (typeof cache[key] === 'undefined') {
    return false;
  }

  const secondsCache = 30;

  // get the creation time of the given key and add 30 seconds to it
  const t1 = new Date(cache[key].time + (1000 * secondsCache));
  const t2 = new Date(); // get the current time
  if (t1 < t2) { // if the current time is greater
    delete cache[key]; // delete the given key from the cache
    return false;
  }

  return true;
};

// get the given key from the cache
exports.get = function get(key) {
  return cache[key];
};

// delete the given key from the cache
exports.delete = function deleteKey(key) {
  delete cache[key];
};

// clear the cache
exports.clear = function clear() {
  cache = {};
};
