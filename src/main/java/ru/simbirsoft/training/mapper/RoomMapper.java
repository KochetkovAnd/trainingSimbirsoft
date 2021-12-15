package ru.simbirsoft.training.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.training.domain.Room;
import ru.simbirsoft.training.dto.RoomDTO;

import java.util.List;

@Mapper
public interface RoomMapper {

    RoomMapper ROOM_MAPPER = Mappers.getMapper(RoomMapper.class);

    Room toEntity(RoomDTO roomDTO);

    @Mapping(target = "id", ignore = true)
    RoomDTO toDTO(Room room);
    List<Room> allToEntity(List<RoomDTO> roomDTOList);
    List<RoomDTO> allToDTO(List<Room> roomList);
}
