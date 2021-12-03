package ru.simbirsoft.trainingSimbirsoft.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.trainingSimbirsoft.domain.Room;
import ru.simbirsoft.trainingSimbirsoft.domain.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private String name;

    private String type;

    private User owner;

    public static RoomDto fromEntityToModel(Room room){
        RoomDto roomDto = new RoomDto();
        roomDto.setName(room.getName());
        roomDto.setType(room.getType());
        roomDto.setOwner(room.getOwner());
        return roomDto;
    }

}
