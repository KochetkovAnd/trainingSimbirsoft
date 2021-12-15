package ru.simbirsoft.training.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.training.domain.Connection;
import ru.simbirsoft.training.dto.ConnectionDTO;

import java.util.List;

@Mapper
public interface ConnectionMapper {

    ConnectionMapper USER_ROOM_MAPPER = Mappers.getMapper(ConnectionMapper.class);

    Connection toEntity(ConnectionDTO connectionDTO);

    @Mapping(target = "id", ignore = true)
    ConnectionDTO toDTO(Connection connection);
    List<Connection> allToEntity(List<ConnectionDTO> connectionDTOList);
    List<ConnectionDTO> allToDTO(List<Connection> connectionList);
}
