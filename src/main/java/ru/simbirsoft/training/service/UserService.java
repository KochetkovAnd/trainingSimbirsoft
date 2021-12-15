package ru.simbirsoft.training.service;


import ru.simbirsoft.training.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll();
    UserDTO getById(Long id);
    UserDTO create(UserDTO userDto);
    UserDTO update(UserDTO userDto);
    boolean deleteById(Long id);
}
