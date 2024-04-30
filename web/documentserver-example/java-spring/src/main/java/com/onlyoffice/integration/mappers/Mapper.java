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

package com.onlyoffice.integration.mappers;

import com.onlyoffice.integration.entities.AbstractEntity;
import com.onlyoffice.integration.documentserver.models.AbstractModel;

// specify the model mapper functions
public interface Mapper<E extends AbstractEntity, M extends AbstractModel> {
   M toModel(E entity);  // convert the entity to the model
}
