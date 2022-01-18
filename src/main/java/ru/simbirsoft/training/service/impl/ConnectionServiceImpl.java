package ru.simbirsoft.training.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.training.domain.Connection;
import ru.simbirsoft.training.domain.User;
import ru.simbirsoft.training.domain.enums.Permission;
import ru.simbirsoft.training.dto.ConnectionDTO;
import ru.simbirsoft.training.dto.ConnectionIdDTO;
import ru.simbirsoft.training.exceptions.NoPermissionException;
import ru.simbirsoft.training.exceptions.ResourceNotFoundException;
import ru.simbirsoft.training.mapper.ConnectionMapper;
import ru.simbirsoft.training.repository.ConnectionRepository;
import ru.simbirsoft.training.repository.RoomRepository;
import ru.simbirsoft.training.repository.UserRepository;
import ru.simbirsoft.training.service.ConnectionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ConnectionDTO> getAll() {
        return allToDTO(connectionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ConnectionDTO getById(Long id) {
        if (connectionRepository.findById(id).isPresent()){
            return toDTO(connectionRepository.getById(id));
        }
        throw new ResourceNotFoundException("No connection found with id = " + id, "");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ConnectionDTO create(ConnectionDTO connectionDTO) {
        connectionRepository.save(toEntity(connectionDTO));
        return connectionDTO;
    }

    @Override
    @Transactional
    public ConnectionDTO update(ConnectionDTO connectionDTO) {
        if (connectionRepository.findById(connectionDTO.getId()).isPresent()){
            create(connectionDTO);
            return connectionDTO;
        }
        throw new ResourceNotFoundException("No connection found with id = " + connectionDTO.getId(), "");
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if(connectionRepository.findById(id).isPresent()) {
            UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByName(securityUser.getUsername());
            if (user.equals(connectionRepository.getById(id).getRoom().getOwner()) || user.getRole().getPermissions().contains(Permission.CONNECTION_REMOVE)) {
                connectionRepository.deleteById(id);
                return (connectionRepository.findById(id).isPresent());
            }
            throw new NoPermissionException("No permission to connection with id = " + id, "");
        }
        throw new ResourceNotFoundException("No connection found with id = " + id, "");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAuthority('connection:create')")
    public ConnectionDTO createById(ConnectionIdDTO connectionIdDTO) {
        if (userRepository.findById(connectionIdDTO.getUser_id()).isPresent()) {
            if (roomRepository.findById(connectionIdDTO.getRoom_id()).isPresent()) {

                ConnectionDTO connectionDTO = new ConnectionDTO(connectionIdDTO.getUser_id(),
                        userRepository.getById(connectionIdDTO.getUser_id()),
                        roomRepository.getById(connectionIdDTO.getRoom_id()));

                connectionRepository.save(toEntity(connectionDTO));
                return connectionDTO;
            }
            throw new ResourceNotFoundException("No room found with id = " + connectionIdDTO.getRoom_id(), "");
        }
        throw new ResourceNotFoundException("No user found with id = " + connectionIdDTO.getUser_id(), "");
    }

    private Connection toEntity(ConnectionDTO connectionDTO){
        return ConnectionMapper.USER_ROOM_MAPPER.toEntity(connectionDTO);
    }

    private ConnectionDTO toDTO(Connection room){
        return ConnectionMapper.USER_ROOM_MAPPER.toDTO(room);
    }

    private List<Connection> allToEntity(List<ConnectionDTO> connectionDTOList){
        return ConnectionMapper.USER_ROOM_MAPPER.allToEntity(connectionDTOList);
    }

    private List<ConnectionDTO> allToDTO(List<Connection> connectionList){
        return ConnectionMapper.USER_ROOM_MAPPER.allToDTO(connectionList);
    }
}
