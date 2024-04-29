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

package entities;

import java.util.List;

public class CommentGroups {
    public List<String> getView() {
        return view;
    }

    public List<String> getEdit() {
        return edit;
    }

    public List<String> getRemove() {
        return remove;
    }

    private List<String> view;
    private List<String> edit;
    private List<String> remove;
    public CommentGroups() {

    }
    public CommentGroups(final List<String> viewParam, final List<String> editParam, final List<String> removeParam) {
        this.view = viewParam;
        this.edit = editParam;
        this.remove = removeParam;
    }
}
