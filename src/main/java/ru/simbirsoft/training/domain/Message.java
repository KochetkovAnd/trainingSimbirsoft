package ru.simbirsoft.training.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "connection_id")
    private Connection connection;

    @Column(name = "text")
    private String text;

    @Column(name = "send_time")
    private Date sendTime;

}
