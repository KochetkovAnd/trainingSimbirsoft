package ru.simbirsoft.trainingSimbirsoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.trainingSimbirsoft.domain.Role;
import ru.simbirsoft.trainingSimbirsoft.domain.Room;
import ru.simbirsoft.trainingSimbirsoft.domain.User;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User_RoomDto {

    private long id;
    private User user;
    private Room room;
    private Role role;
    private long before_ban_role_id;
    private Date unblock_time;

}
