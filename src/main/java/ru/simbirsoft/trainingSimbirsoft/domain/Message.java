package ru.simbirsoft.trainingSimbirsoft.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "message")
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_room_id")
    private User_Room user_room;

    @Column(name = "text")
    private String text;

    @Column(name = "send_time")
    private Date sendTime;

    public Message() {
    }

    public Message(User_Room user_room, String text, Date sendTime) {
        this.user_room = user_room;
        this.text = text;
        this.sendTime = sendTime;
    }
}
