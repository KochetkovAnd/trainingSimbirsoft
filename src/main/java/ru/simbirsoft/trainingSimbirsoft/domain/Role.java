package ru.simbirsoft.trainingSimbirsoft.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "permission_send_message")
    private boolean permission_send_message;

    @Column(name = "permission_get_message")
    private boolean permission_get_message;

    @Column(name = "permission_remove_message")
    private boolean permission_remove_message;

    @Column(name = "permission_create_room")
    private boolean permission_create_room;

    @Column(name = "permission_create_private_room")
    private boolean permission_create_private_room;

    @Column(name = "permission_add_user")
    private boolean permission_add_user;

    @Column(name = "permission_remove_user")
    private boolean permission_remove_user;

    @Column(name = "permission_rename_user")
    private boolean permission_rename_user;

    public Role() {
    }

    public Role(String name, boolean permission_send_message, boolean permission_get_message, boolean permission_remove_message, boolean permission_create_room, boolean permission_create_private_room, boolean permission_add_user, boolean permission_remove_user, boolean permission_rename_user) {
        this.name = name;
        this.permission_send_message = permission_send_message;
        this.permission_get_message = permission_get_message;
        this.permission_remove_message = permission_remove_message;
        this.permission_create_room = permission_create_room;
        this.permission_create_private_room = permission_create_private_room;
        this.permission_add_user = permission_add_user;
        this.permission_remove_user = permission_remove_user;
        this.permission_rename_user = permission_rename_user;
    }
}
