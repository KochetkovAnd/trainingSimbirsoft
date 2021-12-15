package ru.simbirsoft.training.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

}