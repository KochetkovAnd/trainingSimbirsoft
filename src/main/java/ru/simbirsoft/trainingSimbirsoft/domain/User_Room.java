package ru.simbirsoft.trainingSimbirsoft.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user_room")
@Getter
@Setter
public class User_Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "before_ban_role_id")
    private long before_ban_role_id;

    @Column(name = "unblock_time")
    private Date unblock_time;

    public User_Room() {
    }

    public User_Room(User user, Room room, Role role, long before_ban_role_id, Date unblock_time) {
        this.user = user;
        this.room = room;
        this.role = role;
        this.before_ban_role_id = before_ban_role_id;
        this.unblock_time = unblock_time;
    }

}
