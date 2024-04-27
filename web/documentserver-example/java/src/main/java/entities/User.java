// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package entities;

import java.util.List;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private final String group;
    private final List<String> reviewGroups;
    private final CommentGroups commentGroups;
    private final Boolean favorite;
    private final List<String> deniedPermissions;
    private final List<String> descriptions;
    private final Boolean templates;
    private final List<String> userInfoGroups;
    private final Boolean avatar;

    public User(final String idParam, final String nameParam, final String emailParam, final String groupParam,
                final List<String> reviewGroupsParam, final CommentGroups commentGroupsParam,
                final List<String> userInfoGroupsParam, final Boolean favoriteParam,
                final List<String> deniedPermissionsParam, final List<String> descriptionsParam,
                final Boolean templatesParam, final Boolean avatarParam) {
        this.id = idParam;
        this.name = nameParam;
        this.email = emailParam;
        this.group = groupParam;
        this.reviewGroups = reviewGroupsParam;
        this.commentGroups = commentGroupsParam;
        this.favorite = favoriteParam;
        this.deniedPermissions = deniedPermissionsParam;
        this.descriptions = descriptionsParam;
        this.templates = templatesParam;
        this.userInfoGroups = userInfoGroupsParam;
        this.avatar = avatarParam;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }

    public List<String> getReviewGroups() {
        return reviewGroups;
    }

    public CommentGroups getCommentGroups() {
        return commentGroups;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public List<String> getDeniedPermissions() {
        return deniedPermissions;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public Boolean getTemplates() {
        return templates;
    }

    public List<String> getUserInfoGroups() {
        return userInfoGroups;
    }

    public Boolean getAvatar() {
        return avatar;
    }
}
