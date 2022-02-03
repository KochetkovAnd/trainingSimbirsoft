package ru.simbirsoft.training.domain.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public enum Role {
    USER(new HashSet<Permission>(){{
        add(Permission.ROOMS_CREATE_PUBLIC);
        add(Permission.ROOMS_CREATE_PRIVATE);

        add(Permission.CONNECTION_CREATE);

        add(Permission.MESSAGE_SEND);
        add(Permission.MESSAGE_GET);
    }}),

    MODER(new HashSet<Permission>(){{
        add(Permission.USERS_BLOCK);
        add(Permission.USERS_UNBLOCK);

        add(Permission.ROOMS_CREATE_PUBLIC);
        add(Permission.ROOMS_CREATE_PRIVATE);

        add(Permission.CONNECTION_CREATE);

        add(Permission.MESSAGE_SEND);
        add(Permission.MESSAGE_GET);
        add(Permission.MESSAGE_REMOVE);
    }}),

    ADMIN(new HashSet<Permission>(){{
        add(Permission.USERS_BLOCK);
        add(Permission.USERS_UNBLOCK);
        add(Permission.USERS_MAKE_MODERATOR);
        add(Permission.USERS_REMOVE_MODERATOR);

        add(Permission.ROOMS_CREATE_PUBLIC);
        add(Permission.ROOMS_CREATE_PRIVATE);
        add(Permission.ROOMS_RENAME);

        add(Permission.CONNECTION_CREATE);
        add(Permission.CONNECTION_REMOVE);

        add(Permission.MESSAGE_SEND);
        add(Permission.MESSAGE_GET);
        add(Permission.MESSAGE_REMOVE);
    }});

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public  Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
