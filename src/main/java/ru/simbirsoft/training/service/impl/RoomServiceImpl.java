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
        if (!(roomRepository.findById(id).isPresent())) {
            throw new ResourceNotFoundException("Room with id = " + id + " not found", "");
        }
        return toDTO(roomRepository.getById(id));
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
        if (!(roomRepository.findById(roomDTO.getId()).isPresent())) {
            throw new ResourceNotFoundException("Room with id = " + roomDTO.getId() + " not found", "");
        }
        return create(roomDTO);
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
        if (userService.checkBan(userCurrent)) {
            throw new NoPermissionException("You are banned", "");
        }
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName(name);
        roomDTO.setOwner(userCurrent);
        roomDTO.setType(RoomType.PUBLIC);
        roomRepository.save(toEntity(roomDTO));

        connectionService.create(
                new ConnectionDTO(null,
                        userCurrent,
                        roomRepository.findByName(name).get(),
                        null
                )
        );

        return roomDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAuthority('rooms:create_private')")
    public RoomDTO createPrivate(String name) {

        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();
        if (userService.checkBan(userCurrent)) {
            throw new NoPermissionException("You are banned", "");
        }
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName(name);
        roomDTO.setOwner(userCurrent);
        roomDTO.setType(RoomType.PRIVATE);
        roomRepository.save(toEntity(roomDTO));

        connectionService.create(
                new ConnectionDTO(null,
                        userCurrent,
                        roomRepository.findByName(name).get(),
                        null
                )
        );

        return roomDTO;
    }

    @Override
    @Transactional
    public RoomDTO rename(String oldName, String newName) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();
        if (userService.checkBan(userCurrent)) {
            throw new NoPermissionException("You are banned", "");
        }

        if (!(roomRepository.findByName(oldName).isPresent())) {
            throw new ResourceNotFoundException("Room with name = " + oldName + " not found", "");
        }
        Room room = roomRepository.findByName(oldName).get();
        if (!(userCurrent.equals(room.getOwner()) || userCurrent.getRole().getPermissions().contains(Permission.ROOMS_RENAME))) {
            throw new NoPermissionException("No permission to room with name = " + oldName, "");
        }
        RoomDTO roomDTO = toDTO(room);
        roomDTO.setId(room.getId());
        roomDTO.setName(newName);
        return create(roomDTO);
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
