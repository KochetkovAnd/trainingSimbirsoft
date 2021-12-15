package ru.simbirsoft.training.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ru.simbirsoft.training.domain.Room;
import ru.simbirsoft.training.dto.RoomDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-15T21:58:27+0400",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.11 (JetBrains s.r.o.)"
)
public class RoomMapperImpl implements RoomMapper {

    @Override
    public Room toEntity(RoomDTO roomDTO) {
        if ( roomDTO == null ) {
            return null;
        }

        Room room = new Room();

        room.setId( roomDTO.getId() );
        room.setName( roomDTO.getName() );
        room.setType( roomDTO.getType() );
        room.setOwner( roomDTO.getOwner() );

        return room;
    }

    @Override
    public RoomDTO toDTO(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setName( room.getName() );
        roomDTO.setType( room.getType() );
        roomDTO.setOwner( room.getOwner() );

        return roomDTO;
    }

    @Override
    public List<Room> allToEntity(List<RoomDTO> roomDTOList) {
        if ( roomDTOList == null ) {
            return null;
        }

        List<Room> list = new ArrayList<Room>( roomDTOList.size() );
        for ( RoomDTO roomDTO : roomDTOList ) {
            list.add( toEntity( roomDTO ) );
        }

        return list;
    }

    @Override
    public List<RoomDTO> allToDTO(List<Room> roomList) {
        if ( roomList == null ) {
            return null;
        }

        List<RoomDTO> list = new ArrayList<RoomDTO>( roomList.size() );
        for ( Room room : roomList ) {
            list.add( toDTO( room ) );
        }

        return list;
    }
}
