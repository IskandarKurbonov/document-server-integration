// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.mappers;

import com.onlyoffice.integration.entities.AbstractEntity;
import com.onlyoffice.integration.documentserver.models.AbstractModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public abstract class AbstractMapper<E extends AbstractEntity, M extends AbstractModel> implements Mapper<E, M> {
    @Autowired
    private ModelMapper mapper;

    private Class<M> modelClass;

    AbstractMapper(final Class<M> modelClassParam) {
        this.modelClass = modelClassParam;
    }

    @Override
    public M toModel(final E entity) {  // convert the entity to the model
        return Objects.isNull(entity)  // check if an entity is not empty
                ? null
                : mapper.map(entity, modelClass);  // and add it to the model mapper
    }

    Converter<E, M> modelConverter() {  // specify the model converter
        return context -> {
            E source = context.getSource();  // get the source entity
            M destination = context.getDestination();  // get the destination model
            handleSpecificFields(source, destination);  // map the entity to the model
            return context.getDestination();
        };
    }


    void handleSpecificFields(final E source, final M destination) {
    }
}
