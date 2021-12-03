package ru.simbirsoft.trainingSimbirsoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.trainingSimbirsoft.domain.Role;
import ru.simbirsoft.trainingSimbirsoft.domain.Room;
import ru.simbirsoft.trainingSimbirsoft.domain.User;
import ru.simbirsoft.trainingSimbirsoft.domain.User_Room;

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

    public static User_RoomDto fromEntityToModel(User_Room user_room){
        User_RoomDto user_roomDto = new User_RoomDto();
        user_roomDto.setId(user_room.getId());
        user_roomDto.setUser(user_room.getUser());
        user_roomDto.setRoom(user_room.getRoom());
        user_roomDto.setRole(user_room.getRole());
        user_roomDto.setBefore_ban_role_id(user_room.getBefore_ban_role_id());
        user_roomDto.setUnblock_time(user_room.getUnblock_time());
        return user_roomDto;
    }

}
