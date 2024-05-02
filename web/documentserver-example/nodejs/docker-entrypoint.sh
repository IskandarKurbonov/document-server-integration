#!/bin/sh

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

set -e
export NODE_CONFIG='{
	"server": {
		"siteUrl": "'${DS_URL:-"/"}'",
		"token": {
			"enable": '${JWT_ENABLED:-false}',
			"secret": "'${JWT_SECRET:-secret}'",
			"authorizationHeader": "'${JWT_HEADER:-Authorization}'"
		}
	}
}'
exec "$@"
