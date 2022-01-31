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
import ru.simbirsoft.training.domain.enums.Role;
import ru.simbirsoft.training.dto.UserDTO;
import ru.simbirsoft.training.exceptions.NoPermissionException;
import ru.simbirsoft.training.exceptions.ResourceNotFoundException;
import ru.simbirsoft.training.mapper.UserMapper;
import ru.simbirsoft.training.repository.ConnectionRepository;
import ru.simbirsoft.training.repository.MessageRepository;
import ru.simbirsoft.training.repository.UserRepository;
import ru.simbirsoft.training.service.UserService;

import java.util.Date;
import java.util.List;

@Service("userServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Date foreverBanDate = new Date(0);

    private final UserRepository userRepository;
    private final ConnectionRepository connectionRepository;
    private final MessageRepository messageRepository;

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
        return toDTO(userRepository.findByName(userDTO.getName()).get());
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO) {
        if (userRepository.findById(userDTO.getId()).isPresent()){
            create(userDTO);
            return toDTO(userRepository.findByName(userDTO.getName()).get());
        }
        throw new ResourceNotFoundException("No user found with id = " + userDTO.getId(), "");
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if(userRepository.findById(id).isPresent()) {
            for (Connection connection : connectionRepository.findConnectionsByUser(userRepository.getById(id))) {
                messageRepository.deleteMessagesByConnection(connection);
            }
            connectionRepository.deleteConnectionsByUser(userRepository.getById(id));
            userRepository.deleteById(id);
        }
        return (userRepository.findById(id).isPresent());
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('users:make_moderator')")
    public UserDTO makeModer(String username) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();
        if (!checkBan(userCurrent)) {
            if (userRepository.findByName(username).isPresent()){
                User user = userRepository.findByName(username).get();
                UserDTO userDTO = toDTO(user);
                userDTO.setId(user.getId());
                userDTO.setRole(Role.MODER);
                create(userDTO);
                return toDTO(userRepository.findByName(username).get());
            }
            throw new ResourceNotFoundException("No user found with name = " + username, "");
        }
        throw new NoPermissionException("No permission to rename user with userName = " + username, "");
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('users:remove_moderator')")
    public UserDTO removeModer(String username) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();
        if (!checkBan(userCurrent)) {
            if (userRepository.findByName(username).isPresent()){
                User user = userRepository.findByName(username).get();
                UserDTO userDTO = toDTO(user);
                userDTO.setId(user.getId());
                userDTO.setRole(Role.USER);
                create(userDTO);
                return toDTO(userRepository.findByName(username).get());
            }
            throw new ResourceNotFoundException("No user found with name = " + username, "");
        }
        throw new NoPermissionException("No permission to rename user with userName = " + username, "");
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('users:rename')")
    public UserDTO rename(String oldName, String newName) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();
        if (!checkBan(userCurrent)) {
            if (userRepository.findByName(oldName).isPresent()) {
                User user = userRepository.findByName(oldName).get();
                UserDTO userDTO = toDTO(user);
                userDTO.setId(user.getId());
                userDTO.setName(newName);
                return create(userDTO);
            }
            throw new ResourceNotFoundException("No user found with name = " + oldName, "");
        }
        throw new NoPermissionException("No permission to rename user with userName = " + oldName, "");

    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('users:block')")
    public boolean block(String username, Long minutes) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();
        if (!checkBan(userCurrent)) {
            if (userRepository.findByName(username).isPresent()) {
                User user = userRepository.findByName(username).get();
                UserDTO userDTO = toDTO(user);
                userDTO.setId(user.getId());
                Date date;
                if (minutes != null) {
                    date = new Date(new Date().getTime() + minutes * 60 * 1000);
                } else {
                    date = foreverBanDate;
                }
                userDTO.setBannedUntil(date);
                create(userDTO);
                return userRepository.findByName(username).isPresent();
            }
            throw new ResourceNotFoundException("No user found with name = " + username, "");
        }
        throw new NoPermissionException("No permission to block user with name = " + username, "");
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('users:unblock')")
    public UserDTO unblock(String username) {
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userCurrent = userRepository.findByName(securityUser.getUsername()).get();
        if (!checkBan(userCurrent)) {
            if (userRepository.findByName(username).isPresent()) {
                User user = userRepository.findByName(username).get();
                UserDTO userDTO = toDTO(user);
                userDTO.setId(user.getId());
                userDTO.setBannedUntil(null);
                create(userDTO);
                return toDTO(userRepository.findByName(username).get());
            }
            throw new ResourceNotFoundException("No user found with name = " + username, "");
        }
        throw new NoPermissionException("No permission to block user with name = " + username, "");
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

    public boolean checkBan(User user) {
        if (user.getBannedUntil() == null) {
            return false;
        } else {
            Date date = new Date();
            if (date.after(user.getBannedUntil()) && !(user.getBannedUntil().equals(foreverBanDate))) {
                UserDTO userDTO = toDTO(user);
                userDTO.setId(user.getId());
                userDTO.setBannedUntil(null);
                create(userDTO);
                return false;
            } else {
                return true;
            }
        }
    }
}
