// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "`group`")
@Getter
@Setter
public class Group extends AbstractEntity {
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "group")
    private List<User> users;
}
