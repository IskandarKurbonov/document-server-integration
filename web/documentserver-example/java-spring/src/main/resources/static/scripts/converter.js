// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

var ConverExtList;
var EditedExtList;
var UrlConverter;
var UrlEditor;
var FillExtList;

if (typeof jQuery !== "undefined") {
    jQuery.post('/config',
        function(data) {
            FillExtList = data.FillExtList.split(',');
            ConverExtList = data.ConverExtList.split(',');
            EditedExtList = data.EditedExtList.split(',');
            UrlConverter = data.UrlConverter;
            UrlEditor = data.UrlEditor;
    });
}