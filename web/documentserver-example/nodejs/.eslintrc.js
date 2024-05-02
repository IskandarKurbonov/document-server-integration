//  SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
//  SPDX-License-Identifier: Ascensio-System
//
//     Our License onlyoffice.com
//     Empty line
//     Empty line
//     Empty line
//     Empty line
//     Empty line
//     Empty line
//     Empty line
//     

module.exports = {
  env: {
    browser: true,
    commonjs: true,
    es2021: true,
  },
  extends: ['airbnb-base'],
  overrides: [
  ],
  parserOptions: {
    ecmaVersion: 'latest',
  },
  rules: {
    'max-len': ['error', { code: 120 }],
    'no-console': 'off',
    'no-continue': 'off',
    'no-extend-native': ['error', { exceptions: ['String'] }],
    'no-plusplus': ['error', { allowForLoopAfterthoughts: true }],
    'no-prototype-builtins': 'off',
  },
};
