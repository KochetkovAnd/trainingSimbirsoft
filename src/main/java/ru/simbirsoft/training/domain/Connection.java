package ru.simbirsoft.training.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "connection")
@Data
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    private Long before_ban_role_id;

    @Column(name = "unblock_time")
    private Date unblock_time;

}
