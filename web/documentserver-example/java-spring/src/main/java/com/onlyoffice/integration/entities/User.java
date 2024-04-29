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

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "`user`")
@Getter
@Setter
public class User extends AbstractEntity {
    private String name;
    private String email;
    private Boolean favorite;
    @ManyToOne
    private Group group;
    @OneToOne
    private Permission permissions;
//    @Column(columnDefinition = "CLOB")
    @ElementCollection
    @CollectionTable(name = "user_descriptions")
    private List<String> descriptions;
    private Boolean avatar;
    private String image;
}
