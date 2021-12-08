package ru.simbirsoft.trainingSimbirsoft.service;

import ru.simbirsoft.trainingSimbirsoft.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll();
    UserDTO findUserById(Long id);
    UserDTO createUser(UserDTO userDto);
    UserDTO editUser(UserDTO userDto);
    boolean deleteUserById(Long id);
}
