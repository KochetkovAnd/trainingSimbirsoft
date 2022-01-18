package ru.simbirsoft.training.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.training.domain.Room;
import ru.simbirsoft.training.domain.User;
import ru.simbirsoft.training.domain.enums.Permission;
import ru.simbirsoft.training.domain.enums.RoomType;
import ru.simbirsoft.training.dto.RoomDTO;
import ru.simbirsoft.training.exceptions.NoPermissionException;
import ru.simbirsoft.training.exceptions.ResourceNotFoundException;
import ru.simbirsoft.training.mapper.RoomMapper;
import ru.simbirsoft.training.repository.RoomRepository;
import ru.simbirsoft.training.repository.UserRepository;
import ru.simbirsoft.training.service.RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

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
            roomRepository.deleteById(id);
        }
        return (roomRepository.findById(id).isPresent());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAuthority('rooms:create_public')")
    public RoomDTO createPublic(RoomDTO roomDTO) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        roomDTO.setOwner(userRepository.findByName(securityUser.getUsername()));
        roomDTO.setType(RoomType.PUBLIC);
        roomRepository.save(toEntity(roomDTO));
        return roomDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAuthority('rooms:create_private')")
    public RoomDTO createPrivate(RoomDTO roomDTO) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        roomDTO.setOwner(userRepository.findByName(securityUser.getUsername()));
        roomDTO.setType(RoomType.PRIVATE);
        roomRepository.save(toEntity(roomDTO));
        return roomDTO;
    }

    @Override
    @Transactional
    public RoomDTO rename(RoomDTO roomDTO) {
        if (roomRepository.findById(roomDTO.getId()).isPresent()) {


            UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByName(securityUser.getUsername());

            if (user.equals(roomRepository.getById(roomDTO.getId()).getOwner()) || user.getRole().getPermissions().contains(Permission.ROOMS_RENAME)) {
                RoomDTO roomDTO1 = getById(roomDTO.getId());
                roomDTO1.setId(roomDTO.getId());
                roomDTO1.setName(roomDTO.getName());
                /*                List<SimpleGrantedAuthority> authorities = securityUser.getAuthorities().stream()
                        .filter(grantedAuthority -> grantedAuthority instanceof SimpleGrantedAuthority)
                        .map(grantedAuthority -> (SimpleGrantedAuthority) grantedAuthority)
                        .collect(Collectors.toList());

                System.out.println((authorities.stream().anyMatch(authority-> authority.getAuthority().equals(Permission.ROOMS_RENAME.getPermission()))));

                authorities.forEach(authority-> {
                    if(authority.equals(Permission.ROOMS_RENAME.getPermission())) {

                    }
                });

                Set<Permission> authorities = userRepository.findByName(securityUser.getUsername()).getRole().getPermissions();
                System.out.println(authorities);
                System.out.println(authorities.contains(Permission.ROOMS_RENAME));*/
                return create(roomDTO1);

            }
            throw new NoPermissionException("No permission to room with id = " + roomDTO.getId(), "");
        }
        throw new ResourceNotFoundException("No room found with id = " + roomDTO.getId(), "");
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