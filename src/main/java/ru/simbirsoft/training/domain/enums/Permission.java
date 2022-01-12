package ru.simbirsoft.training.domain.enums;

public enum Permission {

    USERS_BLOCK("users:block"),
    USERS_UNBLOCK("users:unblock"),
    USERS_MAKE_MODERATOR("users:make_moderator"),
    USERS_REMOVE_MODERATOR("users:remove_moderator");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
