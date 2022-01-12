package ru.simbirsoft.training.domain.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public enum Role {
    USER(new HashSet<Permission>(){{

    }}),

    MODER(new HashSet<Permission>(){{
        add(Permission.USERS_BLOCK);
        add(Permission.USERS_UNBLOCK);
    }}),


    ADMIN(new HashSet<Permission>(){{
        add(Permission.USERS_BLOCK);
        add(Permission.USERS_UNBLOCK);
        add(Permission.USERS_MAKE_MODERATOR);
        add(Permission.USERS_REMOVE_MODERATOR);
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
