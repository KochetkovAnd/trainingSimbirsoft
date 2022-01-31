package ru.simbirsoft.training.domain;

import lombok.Data;
import ru.simbirsoft.training.domain.enums.Role;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @JoinColumn(name = "bannedUntil")
    private Date bannedUntil;
}

