package ru.simbirsoft.trainingSimbirsoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.trainingSimbirsoft.domain.User;
import ru.simbirsoft.trainingSimbirsoft.dto.UserDTO;
import ru.simbirsoft.trainingSimbirsoft.mapper.UserMapper;
import ru.simbirsoft.trainingSimbirsoft.repository.UserRepository;
import ru.simbirsoft.trainingSimbirsoft.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<UserDTO> getAll() {
        return allToDTO(userRepository.findAll());
    }

    @Override
    public UserDTO findUserById(Long id) {
        return toDTO(userRepository.getById(id));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userRepository.save(toEntity(userDTO));
        return toDTO(userRepository.getById(userDTO.getId()));
    }

    @Override
    public UserDTO editUser(UserDTO userDTO) {
        if (userRepository.findById(userDTO.getId()).isPresent()){
            createUser(userDTO);
        }
        return toDTO(userRepository.getById(userDTO.getId()));
    }

    @Override
    public boolean deleteUserById(Long id) {
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
        return (userRepository.findById(id).isPresent());
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
