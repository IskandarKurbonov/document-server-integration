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

package com.onlyoffice.integration.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "`permission`")
@Getter
@Setter
public class Permission extends AbstractEntity {
    private Boolean comment = true;
    private Boolean copy = true;
    private Boolean download = true;
    private Boolean edit = true;
    private Boolean print = true;
    private Boolean fillForms = true;
    private Boolean modifyFilter = true;
    private Boolean modifyContentControl = true;
    private Boolean review = true;
    private Boolean chat = true;
    private Boolean templates = true;
    @ManyToMany
    private List<Group> reviewGroups;
    @ManyToMany
    private List<Group> commentsViewGroups;
    @ManyToMany
    private List<Group> commentsEditGroups;
    @ManyToMany
    private List<Group> commentsRemoveGroups;
    @ManyToMany
    private List<Group> userInfoGroups;
    private Boolean protect = true;
}
