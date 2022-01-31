package ru.simbirsoft.training.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.training.domain.Room;
import ru.simbirsoft.training.domain.User;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDTO {

    private Long id;
    private User user;
    private Room room;
    private Date disconnectUntil;

}
