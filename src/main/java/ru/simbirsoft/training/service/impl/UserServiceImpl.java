package ru.simbirsoft.training.service.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.training.domain.User;
import ru.simbirsoft.training.domain.enums.Role;
import ru.simbirsoft.training.dto.UserDTO;
import ru.simbirsoft.training.exceptions.ResourceNotFoundException;
import ru.simbirsoft.training.mapper.UserMapper;
import ru.simbirsoft.training.repository.UserRepository;
import ru.simbirsoft.training.service.UserService;

import java.util.List;

@Service("userServiceImpl")

public class UserServiceImpl implements UserService {

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        return allToDTO(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getById(Long id) {
        if (userRepository.findById(id).isPresent()){
            return toDTO(userRepository.getById(id));
        }
        throw new ResourceNotFoundException("No user found with id = " + id, "");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDTO create(UserDTO userDTO) {
        userRepository.save(toEntity(userDTO));
        return toDTO(userRepository.findByName(userDTO.getName()));
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO) {
        if (userRepository.findById(userDTO.getId()).isPresent()){
            create(userDTO);
            return toDTO(userRepository.findByName(userDTO.getName()));
        }
        throw new ResourceNotFoundException("No user found with id = " + userDTO.getId(), "");

    }

    @Override
    @Transactional

    public boolean deleteById(Long id) {
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
        return (userRepository.findById(id).isPresent());
    }


    @Override
    @Transactional
    @PreAuthorize("hasAuthority('users:block')")
    public UserDTO blockById(Long id) {
        if (userRepository.findById(id).isPresent()){
            UserDTO userDTO = getById(id);
            userDTO.setId(id);
            userDTO.setRole(Role.BLOCKED);
            create(userDTO);
            return toDTO(userRepository.getById(id));
        }
        throw new ResourceNotFoundException("No user found with id = " + id, "");
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('users:unblock')")
    public UserDTO unblockById(Long id) {
        if (userRepository.findById(id).isPresent()){
            UserDTO userDTO = getById(id);
            userDTO.setId(id);
            userDTO.setRole(Role.USER);
            create(userDTO);
            return toDTO(userRepository.getById(id));
        }
        throw new ResourceNotFoundException("No user found with id = " + id, "");
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('users:make_moderator')")
    public UserDTO makeModer(Long id) {
        if (userRepository.findById(id).isPresent()){
            UserDTO userDTO = getById(id);
            userDTO.setId(id);
            userDTO.setRole(Role.MODER);
            create(userDTO);
            return toDTO(userRepository.getById(id));
        }
        throw new ResourceNotFoundException("No user found with id = " + id, "");
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('users:remove_moderator')")
    public UserDTO removeModer(Long id) {
        if (userRepository.findById(id).isPresent()){
            UserDTO userDTO = getById(id);
            userDTO.setId(id);
            userDTO.setRole(Role.USER);
            create(userDTO);
            return toDTO(userRepository.getById(id));
        }
        throw new ResourceNotFoundException("No user found with id = " + id, "");
    }

    private User toEntity(UserDTO userDto){
        return UserMapper.USER_MAPPER.toEntity(userDto);
    }

    private UserDTO toDTO(User user){
        return UserMapper.USER_MAPPER.toDTO(user);
    }

    private List<User> allToEntity(List<UserDTO> userDtoList){
        return UserMapper.USER_MAPPER.allToEntity(userDtoList);
    }

    private List<UserDTO> allToDTO(List<User> userList){
        return UserMapper.USER_MAPPER.allToDTO(userList);
    }
}
