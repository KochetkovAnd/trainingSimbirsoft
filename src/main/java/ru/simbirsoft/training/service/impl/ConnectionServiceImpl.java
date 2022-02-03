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
        if (connectionRepository.findById(id).isPresent()){
            return toDTO(connectionRepository.getById(id));
        }
        throw new ResourceNotFoundException("No connection found with id = " + id, "");
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
        if (connectionRepository.findById(connectionDTO.getId()).isPresent()){
            create(connectionDTO);
            return connectionDTO;
        }
        throw new ResourceNotFoundException("No connection found with id = " + connectionDTO.getId(), "");
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('connection:remove')")
    public boolean deleteById(Long id) {
        if(connectionRepository.findById(id).isPresent()) {
            UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByName(securityUser.getUsername()).get();
            if (user.equals(connectionRepository.getById(id).getRoom().getOwner()) || user.getRole().getPermissions().contains(Permission.CONNECTION_REMOVE)) {
                messageRepository.deleteMessagesByConnection(connectionRepository.getById(id));
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
    public ConnectionDTO createOther(String userName, String roomName) {
        if (userRepository.findByName(userName).isPresent()) {
            if (roomRepository.findByName(roomName).isPresent()) {
                UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = userRepository.findByName(securityUser.getUsername()).get();
                if (checkLocate(user, roomName) || user.equals(roomRepository.findByName(roomName).get().getOwner())) {
                    if (!(checkDisconnect(user, roomName)) && (!userService.checkBan(user))) {

                        if (!(roomRepository.findByName(roomName).get().getType().equals(RoomType.PRIVATE)
                                && connectionRepository.findConnectionsByRoom(roomRepository.findByName(roomName).get()).size() >= 2)) {


                            ConnectionDTO connectionDTO = new ConnectionDTO(null,
                                    userRepository.findByName(userName).get(),
                                    roomRepository.findByName(roomName).get(),
                                    null);

                            connectionRepository.save(toEntity(connectionDTO));
                            return connectionDTO;
                        }
                        throw new NoPermissionException("private room already full", "");
                    }
                    throw new NoPermissionException("you are disconnect from room named = " + roomName + " or banned", "");
                }
                throw new NoPermissionException("you are not a member of a room named = " + roomName, "");
            }
            throw new ResourceNotFoundException("No room found with name = " + roomName, "");
        }
        throw new ResourceNotFoundException("No user found with name = " + userName, "");
    }

    @Override
    @Transactional
    public boolean disconnectSelf(String roomName) {
        if (roomRepository.findByName(roomName).isPresent()) {

            UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByName(securityUser.getUsername()).get();

            if (connectionRepository.findByUserAndRoom(user, roomRepository.findByName(roomName).get()).isPresent()) {
                connectionRepository.deleteById(roomRepository.findByName(roomName).get().getId());
            }
            throw new ResourceNotFoundException("No connection found with user name  = " + user.getName() + " room name = " + roomName, "");
        }
        throw new ResourceNotFoundException("No room found with name = " + roomName, "");
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('connection:remove')")
    public boolean disconnectOther(String userName, String roomName, Long minutes) {
        if (userRepository.findByName(userName).isPresent()) {
            if (roomRepository.findByName(roomName).isPresent()) {
                if (connectionRepository.findByUserAndRoom(userRepository.findByName(userName).get(), roomRepository.findByName(roomName).get()).isPresent()) {

                    UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    User user = userRepository.findByName(securityUser.getUsername()).get();

                    if (user.equals(roomRepository.findByName(roomName).get().getOwner()) || user.getRole().getPermissions().contains(Permission.CONNECTION_REMOVE)) {
                        Connection connection = connectionRepository.findByUserAndRoom(userRepository.findByName(userName).get(), roomRepository.findByName(roomName).get()).get();
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
                        return (connectionRepository.findByUserAndRoom(userRepository.findByName(userName).get(), roomRepository.findByName(roomName).get()).isPresent());
                    }
                    throw new NoPermissionException("No permission to connection with user name = " + userName + " room name =  " + roomName, "");
                }
                throw new ResourceNotFoundException("No connection found with user name  = " + userName + " room name = " + roomName, "");
            }
            throw new ResourceNotFoundException("No room found with name = " + roomName, "");
        }
        throw new ResourceNotFoundException("No user found with name = " + userName, "");
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

    public boolean checkDisconnect(User user,String roomName) {

        Connection connection = connectionRepository.findByUserAndRoom(user, roomRepository.findByName(roomName).get()).get();

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

    public boolean checkLocate(User user,String roomName) {
        return connectionRepository.findByUserAndRoom(user, roomRepository.findByName(roomName).get()).isPresent();
    }
}
