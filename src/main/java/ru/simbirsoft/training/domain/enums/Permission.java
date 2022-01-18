package ru.simbirsoft.training.domain.enums;

public enum Permission {

    USERS_BLOCK("users:block"),
    USERS_UNBLOCK("users:unblock"),
    USERS_MAKE_MODERATOR("users:make_moderator"),
    USERS_REMOVE_MODERATOR("users:remove_moderator"),

    ROOMS_CREATE_PUBLIC("rooms:create_public"),
    ROOMS_CREATE_PRIVATE("rooms:create_private"),
    ROOMS_RENAME("rooms:rename"),

    CONNECTION_CREATE("connection:create"),
    CONNECTION_REMOVE("connection:remove"),

    MESSAGE_SEND("message:send"),
    MESSAGE_GET("message:get"),
    MESSAGE_REMOVE("message:remove");



    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
