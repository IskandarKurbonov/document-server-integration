// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

// generate 16 octet
const s4 = function s4() {
  return Math.trunc((1 + Math.random()) * 0x10000).toString(16)
    .substring(1);
};

// create uuid v4
exports.newGuid = function newGuid() {
  return (`${s4() + s4()}-${s4()}-${s4()}-${s4()}-${s4()}${s4()}${s4()}`);
};
