package ru.simbirsoft.training.domain;


import lombok.Data;
import ru.simbirsoft.training.domain.enums.RoomType;

import javax.persistence.*;

@Entity
@Table(name = "room")
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    @JoinColumn(name = "type")
    private RoomType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;

}