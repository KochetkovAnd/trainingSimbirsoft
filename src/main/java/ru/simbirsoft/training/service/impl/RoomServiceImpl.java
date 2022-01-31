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
import ru.simbirsoft.training.dto.RoomDTO;
import ru.simbirsoft.training.exceptions.NoPermissionException;
import ru.simbirsoft.training.exceptions.ResourceNotFoundException;
import ru.simbirsoft.training.mapper.RoomMapper;
import ru.simbirsoft.training.repository.ConnectionRepository;
import ru.simbirsoft.training.repository.MessageRepository;
import ru.simbirsoft.training.repository.RoomRepository;
import ru.simbirsoft.training.repository.UserRepository;
import ru.simbirsoft.training.service.RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ConnectionRepository connectionRepository;
    private final MessageRepository messageRepository;

    private final UserServiceImpl userService;
    private final ConnectionServiceImpl connectionService;



    @Override
    @Transactional(readOnly = true)
    public List<RoomDTO> getAll() {
        return allToDTO(roomRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public RoomDTO getById(Long id) {
        if (roomRepository.findById(id).isPresent()) {
            return toDTO(roomRepository.getById(id));
        }
        throw new ResourceNotFoundException("No room found with id = " + id, "");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RoomDTO create(RoomDTO roomDTO) {
        roomRepository.save(toEntity(roomDTO));
        return roomDTO;
    }

    @Override
    @Transactional
    public RoomDTO update(RoomDTO roomDTO) {
        if (roomRepository.findById(roomDTO.getId()).isPresent()){
            return create(roomDTO);
        }
        throw new ResourceNotFoundException("No room found with id = " + roomDTO.getId(), "");
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if(roomRepository.findById(id).isPresent()) {

            for (Connection connection : connectionRepository.findConnectionsByRoom(roomRepository.getById(id))) {
                messageRepository.deleteMessagesByConnection(connection);
            }
            connectionRepository.deleteConnectionsByRoom(roomRepository.getById(id));
            roomRepository.deleteById(id);
        }
        return (roomRepository.findById(id).isPresent());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAuthority('rooms:create_public')")
    public RoomDTO createPublic(String name) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();
        if (!userService.checkBan(userCurrent)) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setName(name);
            roomDTO.setOwner(userRepository.findByName(securityUser.getUsername()).get());
            roomDTO.setType(RoomType.PUBLIC);
            roomRepository.save(toEntity(roomDTO));

            connectionService.create(
                    new ConnectionDTO(null,
                            userRepository.findByName(securityUser.getUsername()).get(),
                            roomRepository.findByName(name).get(),
                            null
                    )
            );

            return roomDTO;
        }
        throw new NoPermissionException("No permission to create public room", "");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAuthority('rooms:create_private')")
    public RoomDTO createPrivate(String name) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();
        if (!userService.checkBan(userCurrent)) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setName(name);
            roomDTO.setOwner(userRepository.findByName(securityUser.getUsername()).get());
            roomDTO.setType(RoomType.PRIVATE);
            roomRepository.save(toEntity(roomDTO));

            connectionService.create(
                    new ConnectionDTO(null,
                            userRepository.findByName(securityUser.getUsername()).get(),
                            roomRepository.findByName(name).get(),
                            null
                    )
            );

            return roomDTO;
        }
        throw new NoPermissionException("No permission to create private room", "");
    }

    @Override
    @Transactional
    public RoomDTO rename(String oldName, String newName) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByName(securityUser.getUsername()).get();
        if (!userService.checkBan(user)) {
            if (roomRepository.findByName(oldName).isPresent()) {
                if (user.equals(roomRepository.findByName(oldName).get().getOwner()) || user.getRole().getPermissions().contains(Permission.ROOMS_RENAME)) {
                    Room room = roomRepository.findByName(oldName).get();
                    RoomDTO roomDTO = toDTO(room);
                    roomDTO.setId(room.getId());
                    roomDTO.setName(newName);
                    return create(roomDTO);
                }
                throw new NoPermissionException("No permission to room with name = " + oldName, "");
            }
            throw new ResourceNotFoundException("No room found with name = " + oldName, "");
        }
        throw new NoPermissionException("No permission to room with name = " + oldName, "");
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {

        if (roomRepository.findByName(name).isPresent()) {
            deleteById(roomRepository.findByName(name).get().getId());
        }
        return (roomRepository.findByName(name).isPresent());
    }


    private Room toEntity(RoomDTO roomDTO){
        return RoomMapper.ROOM_MAPPER.toEntity(roomDTO);
    }

    private RoomDTO toDTO(Room room){
        return RoomMapper.ROOM_MAPPER.toDTO(room);
    }

    private List<Room> allToEntity(List<RoomDTO> roleDTOList){
        return RoomMapper.ROOM_MAPPER.allToEntity(roleDTOList);
    }

    private List<RoomDTO> allToDTO(List<Room> roomList){
        return RoomMapper.ROOM_MAPPER.allToDTO(roomList);
    }
}
