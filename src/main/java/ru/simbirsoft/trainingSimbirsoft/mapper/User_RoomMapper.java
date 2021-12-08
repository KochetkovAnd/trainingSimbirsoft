package ru.simbirsoft.trainingSimbirsoft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.trainingSimbirsoft.domain.User_Room;
import ru.simbirsoft.trainingSimbirsoft.dto.User_RoomDTO;

import java.util.List;

@Mapper
public interface User_RoomMapper {

    User_RoomMapper USER_ROOM_MAPPER = Mappers.getMapper(User_RoomMapper.class);

    User_Room toEntity(User_RoomDTO user_roomDTO);

    @Mapping(target = "id", ignore = true)
    User_RoomDTO toDTO(User_Room user_room);
    List<User_Room> allToEntity(List<User_RoomDTO> user_roomDTOList);
    List<User_RoomDTO> allToDTO(List<User_Room> user_roomList);
}
