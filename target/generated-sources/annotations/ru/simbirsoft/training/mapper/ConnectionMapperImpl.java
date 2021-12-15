package ru.simbirsoft.training.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ru.simbirsoft.training.domain.Connection;
import ru.simbirsoft.training.dto.ConnectionDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-15T21:58:27+0400",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.11 (JetBrains s.r.o.)"
)
public class ConnectionMapperImpl implements ConnectionMapper {

    @Override
    public Connection toEntity(ConnectionDTO connectionDTO) {
        if ( connectionDTO == null ) {
            return null;
        }

        Connection connection = new Connection();

        connection.setId( connectionDTO.getId() );
        connection.setUser( connectionDTO.getUser() );
        connection.setRoom( connectionDTO.getRoom() );
        connection.setRole( connectionDTO.getRole() );
        connection.setBefore_ban_role_id( connectionDTO.getBefore_ban_role_id() );
        connection.setUnblock_time( connectionDTO.getUnblock_time() );

        return connection;
    }

    @Override
    public ConnectionDTO toDTO(Connection connection) {
        if ( connection == null ) {
            return null;
        }

        ConnectionDTO connectionDTO = new ConnectionDTO();

        connectionDTO.setUser( connection.getUser() );
        connectionDTO.setRoom( connection.getRoom() );
        connectionDTO.setRole( connection.getRole() );
        connectionDTO.setBefore_ban_role_id( connection.getBefore_ban_role_id() );
        connectionDTO.setUnblock_time( connection.getUnblock_time() );

        return connectionDTO;
    }

    @Override
    public List<Connection> allToEntity(List<ConnectionDTO> connectionDTOList) {
        if ( connectionDTOList == null ) {
            return null;
        }

        List<Connection> list = new ArrayList<Connection>( connectionDTOList.size() );
        for ( ConnectionDTO connectionDTO : connectionDTOList ) {
            list.add( toEntity( connectionDTO ) );
        }

        return list;
    }

    @Override
    public List<ConnectionDTO> allToDTO(List<Connection> connectionList) {
        if ( connectionList == null ) {
            return null;
        }

        List<ConnectionDTO> list = new ArrayList<ConnectionDTO>( connectionList.size() );
        for ( Connection connection : connectionList ) {
            list.add( toDTO( connection ) );
        }

        return list;
    }
}
