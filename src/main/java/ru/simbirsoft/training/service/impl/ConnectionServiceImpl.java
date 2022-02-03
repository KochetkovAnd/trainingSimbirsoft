package ru.simbirsoft.training.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.training.domain.Connection;
import ru.simbirsoft.training.domain.Room;
import ru.simbirsoft.training.domain.User;
import ru.simbirsoft.training.domain.enums.Permission;
import ru.simbirsoft.training.domain.enums.RoomType;
import ru.simbirsoft.training.dto.ConnectionDTO;
import ru.simbirsoft.training.exceptions.NoPermissionException;
import ru.simbirsoft.training.exceptions.ResourceNotFoundException;
import ru.simbirsoft.training.mapper.ConnectionMapper;
import ru.simbirsoft.training.repository.ConnectionRepository;
import ru.simbirsoft.training.repository.MessageRepository;
import ru.simbirsoft.training.repository.RoomRepository;
import ru.simbirsoft.training.repository.UserRepository;
import ru.simbirsoft.training.service.ConnectionService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ConnectionRepository connectionRepository;
    private final MessageRepository messageRepository;

    private final UserServiceImpl userService;

    private static final Date foreverDisconnectDate = new Date(0);

    @Override
    @Transactional(readOnly = true)
    public List<ConnectionDTO> getAll() {
        return allToDTO(connectionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ConnectionDTO getById(Long id) {
        if (!(connectionRepository.findById(id).isPresent())) {
            throw new ResourceNotFoundException("Connection with id = " + id + " not found", "");
        }
        return toDTO(connectionRepository.getById(id));
    }

    @Override
    @Transactional
    public ConnectionDTO create(ConnectionDTO connectionDTO) {
        connectionRepository.save(toEntity(connectionDTO));
        return connectionDTO;
    }

    @Override
    @Transactional
    public ConnectionDTO update(ConnectionDTO connectionDTO) {
        if (!(connectionRepository.findById(connectionDTO.getId()).isPresent())){
            throw new ResourceNotFoundException("Connection with id = " + connectionDTO.getId() + " not found", "");
        }
        return create(connectionDTO);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {

        if (!(connectionRepository.findById(id).isPresent())) {
            throw new ResourceNotFoundException("Connection with id = " + id + " not found", "");
        }
        Connection connection = connectionRepository.findById(id).get();
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByName(securityUser.getUsername()).get();
        if (!(user.equals(connection.getRoom().getOwner()) || user.getRole().getPermissions().contains(Permission.CONNECTION_REMOVE))) {
            throw new NoPermissionException("No permission to connection with id = " + id, "");
        }
        messageRepository.deleteMessagesByConnection(connection);
        connectionRepository.deleteById(id);
        return (connectionRepository.findById(id).isPresent());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAuthority('connection:create')")
    public ConnectionDTO createOther(String userName, String roomName) {
        if (!(userRepository.findByName(userName).isPresent())) {
            throw new ResourceNotFoundException("User with name = " + userName + " not found", "");
        }
        User user = userRepository.findByName(userName).get();

        if (!(roomRepository.findByName(roomName).isPresent())) {
            throw new ResourceNotFoundException("Room with name = " + roomName + " not found", "");
        }
        Room room = roomRepository.findByName(roomName).get();

        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();

        if (!(checkLocate(userCurrent, room))) {
            throw new NoPermissionException("You are not a member of a room named = " + roomName, "");
        }

        if ((checkDisconnect(userCurrent, room)) || (userService.checkBan(userCurrent))) {
            throw new NoPermissionException("You are disconnect from room named = " + roomName + " or banned globally", "");
        }

        if (room.getType().equals(RoomType.PRIVATE)
                && connectionRepository.findConnectionsByRoom(room).size() >= 2) {
            throw new NoPermissionException("Private room with name = "+ roomName +"already full", "");
        }
        ConnectionDTO connectionDTO = new ConnectionDTO(null, user, room, null);

        connectionRepository.save(toEntity(connectionDTO));
        return connectionDTO;
    }

    @Override
    @Transactional
    public boolean disconnectSelf(String roomName) {
        if (!(roomRepository.findByName(roomName).isPresent())) {
            throw new ResourceNotFoundException("Room with name = " + roomName + " not found", "");
        }
        Room room = roomRepository.findByName(roomName).get();

        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByName(securityUser.getUsername()).get();

        if (!(connectionRepository.findByUserAndRoom(user, room).isPresent())) {
            throw new ResourceNotFoundException("Connection with userName = " + user.getName() + " and roomName = " + roomName+ " not found", "");
        }
        Connection connection = connectionRepository.findByUserAndRoom(user, room).get();

        connectionRepository.deleteById(connection.getId());
        return (connectionRepository.findByUserAndRoom(user, room).isPresent());
    }

    @Override
    @Transactional
    public boolean disconnectOther(String userName, String roomName, Long minutes) {

        if (!(userRepository.findByName(userName).isPresent())) {
            throw new ResourceNotFoundException("User with name = " + userName + " not found", "");
        }
        User user = userRepository.findByName(userName).get();

        if (!(roomRepository.findByName(roomName).isPresent())) {
            throw new ResourceNotFoundException("Room with name = " + roomName + " not found", "");
        }
        Room room = roomRepository.findByName(roomName).get();

        if (!(connectionRepository.findByUserAndRoom(user, room).isPresent())) {
            throw new ResourceNotFoundException("Connection with userName  = " + userName + " and roomName = " + roomName + " not found", "");
        }
        Connection connection = connectionRepository.findByUserAndRoom(user, room).get();

        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();

        if (!(userCurrent.equals(room.getOwner()) || userCurrent.getRole().getPermissions().contains(Permission.CONNECTION_REMOVE))) {
            throw new NoPermissionException("No permission to connection with userName = " + userName + "and  roomName =  " + roomName, "");
        }

        ConnectionDTO connectionDTO = toDTO(connection);
        connectionDTO.setId(connection.getId());
        Date date;
        if (minutes != null) {
            date = new Date(new Date().getTime() + minutes * 60 * 1000);
        } else {
            date = foreverDisconnectDate;
        }
        connectionDTO.setDisconnectUntil(date);
        create(connectionDTO);
        return (connectionRepository.findByUserAndRoom(user, room).isPresent());
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

    public boolean checkDisconnect(User user,Room room) {

        Connection connection = connectionRepository.findByUserAndRoom(user, room).get();

        if (connection.getDisconnectUntil() == null) {
            return false;
        } else {
            Date date = new Date();
            if (date.after(connection.getDisconnectUntil()) && !(connection.getDisconnectUntil().equals(foreverDisconnectDate))) {
                ConnectionDTO connectionDTO = toDTO(connection);
                connectionDTO.setId(connection.getId());
                connectionDTO.setDisconnectUntil(null);
                create(connectionDTO);
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean checkLocate(User user,Room room) {
        return connectionRepository.findByUserAndRoom(user, room).isPresent();
    }
}

